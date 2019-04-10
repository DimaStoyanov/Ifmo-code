# variant 14

clc
clear

steps = 78;
h = 0.09;                                   % дискретизация

n = 100;                                      % мест в очереди
m =  2;                                      % приборов
la = 3;                                     % интенсивность заявок 
mu = 4;                                     % интенсивность обработки
gamma = 0;                                % интенсивность усталости в очереди
delta = 2;  #                            % интенсивность усталости в системе
                                            % Усталость - leave без обработки
La = la * ones(1, m+n);
Mu = mu * [cumsum(ones(1, m)), m * ones(1, n)] + ...
     gamma * [zeros(1, m), cumsum(ones(1, n))] + ...
     delta * cumsum(ones(1, m+n));

De = [La, 0] + [0, Mu];                     % главная диагональ
L = diag(La, 1) - diag(De) + diag(Mu, -1);  % инфинитозимальная матрица
P = expm(h * L);                            % матрица переходов
                                            % (см ДУ Колмогорова)
frac = [1, La ./ Mu];
k = cumprod(frac);
p0 = 1 / sum(k);
p_th = k .* p0;                             % теор. вер-ти состояний системы

p_0 = zeros(1, m+n+1);                      % начальные вероятности
p_0(1) = 1;                                 % в момент t_0 -> [1000..0]
P_n(1, :) = p_0;

for step = 1:steps
    P_n = [P_n; P_n(step, :)*P];
end
P_n = [P_n; p_th];                          % вер-ти состояний in real time(RT)
P_q = sum(P_n(:, m+2 : m+n+1), 2);

x = [0:steps, steps+1];
figure(1)
plot(x, P_n);

E_q = P_n * [zeros(1, m), 0 : n]';          % Е(кол-во заявок в очереди) RT
E_Q = P_n * [0 : m+n]';                     % Е(кол-во заявок в системе) RT
figure(2)
plot(x, E_Q, x, E_q);

% Ниже - теор. данные для системы в стационарном режиме
P_theor = P_n(end, 1:10)'                                % теор. вер-ти состояний системы
P_q_theor = P_q(end)                                    % вер-ть очереди
E_Q_th = E_Q(end)                           % Е(кол-во заявок в системе)
E_q_th = E_q(end)                           % Е(кол-во заявок в очереди)
E_W = E_q_th / la                           % E(время в очереди)
E_T = E_Q_th / la                           % E(время в системе)
E_a = sum(P_n(end, 1 : m+1) .* [0 : m])     % E(занятые приборы) 

% Ниже - практ. данные для системы в установившемся режиме
P_pract = P_n(end-1, 1:10)'
P_q_pract = P_q(end-1)
E_Q_pr = E_Q(end-1)
E_q_pr = E_q(end-1)
E_W_pr = E_q_pr / la
E_T_pr = E_Q_pr / la
E_a_pr = sum(P_n(end-1, 1 : m+1) .* [0 : m])

