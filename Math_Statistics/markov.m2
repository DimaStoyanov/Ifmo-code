clc
clear

p = 0.4
q = 1 - p;
N = 7;


p_0 = zeros(N, 1);
x = round(unifrnd(2, N - 1));
p_0(x)=1;

pp = shift(eye(N) * q, 1) + shift(eye(N) * p, -1);
pp(1, :) = eye(1, N);
pp(N, :) = flip(eye(1, N));


#printf('State probability after 5 steps\n')
k = 5;
pp ** k;


#printf('State probability after 20 steps\n')
k = 20;
pp ** k;


#printf('State probability after 100 steps\n')
k = 100;
p_pract = pp ** k;

p_theor_0 = zeros(N, 1);
for x = 1:N
  p_theor_0(x) = ((q / p) ** x - (q / p) ** (N  -1)) / (1 - (q / p) ** (N - 1));
endfor  
#printf("P_pract[0]  P_theor[0] Delta\n")
[p_pract(:, 1), p_theor_0, abs(p_pract(1) - p_theor_0)];


x_path = zeros(N, 1);
x_path(1) = round(unifrnd(2, N - 1));
x_path_1(1) = round(unifrnd(2, N - 1));
yuk = zeros(N, 1)';
yuk(x_path(1)) = 1;
theor = (yuk * (pp ** 500))(1);
pract = p_theor_0(x_path(1) - 1);
[x_path(1), theor, pract]


yuk1 = zeros(N, 1)';
yuk1(x_path_1(1)) = 1;
theor = (yuk1 * (pp ** 500))(1);
pract = p_theor_0(x_path_1(1) - 1);
[x_path_1(1), theor, pract]


# Plot trajectory of wandering
k = 20;
x_end = zeros(k, N);
count_markov = 10000;
for j = 1:count_markov
  x_path = zeros(k, 1);
  x_path(1) = round(unifrnd(2, N - 1));
  for i = 2:k
    state = zeros(N, 1)';
    state(x_path(i - 1)) = 
    if x_path(i - 1) == 1 || x_path(i - 1) == N
      x_path(i) = x_path(i - 1);
    else
      x_path(i) = (x_path(i - 1) + 1) * (rnd < p) + (x_path(i - 1) - 1) * (rnd >= p);
    endif
    x_end(i, x_path(i)) += 1;
  endfor
endfor
x_end /= count_markov;


plot(x_path, '--*')

figure
plot(2:k, x_end(2:end, :))

