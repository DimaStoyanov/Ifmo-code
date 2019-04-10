# variant 4

clc
clear

steps = 200;
h = 0.04;                                   % дискретизация

n0 = 100;                                      % мест в очереди
m0 = 100;                                      % приборов
s = 4;                                      % источников
la = 2;                                     % интенсивность заявок
mu = 3;                                      % интенсивность обработки

m = min(m0, s)
n = min(m0+n0, s) - m

La = la * fliplr(cumsum(ones(1, m+n)))
Mu = mu * [cumsum(ones(1, m)), m * ones(1, n)]

De = [La, 0] + [0, Mu]                      % главная диагональ
L = diag(La, 1) - diag(De) + diag(Mu, -1)   % инфинитозимальная матрица
P = expm(h * L);                            % матрица переходов
                                            % (см ДУ Колмогорова)
frac = [1, La ./ Mu];
k = cumprod(frac);
p0 = 1 / sum(k);
p_th = k .* p0;                             % теор вероятности состояния
p_th_res = p_th'                                       % системы в стац. режиме

p_0 = zeros(1, m+n+1);                      % начальные вероятности
p_0(1) = 1;                                 % в момент t_0 -> [1000..0]
P_n(1, :) = p_0;

for step = 1:steps
    P_n = [P_n; P_n(step, :)*P];
end
p_pract = P_n(end, :)'
P_n = [P_n; p_th];

x = [1:steps+1, steps+2];
figure(1)
plot(x, P_n)

Q_t = P_n * [0 : m+n]';                   % Е(кол-во заявок в системе)
q_t = P_n * [zeros(1, m), 0 : n]';        % Е(кол-во заявок в очереди)
figure(2)
plot(x, Q_t, x, q_t)
grid
Q_t_res = Q_t(end)
q_t_res = q_t(end)

