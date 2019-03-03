clc
clear

p = 0.49
q = 1 - p;
N = 10;


p_0 = zeros(N, 1);
x = round(unifrnd(2, N - 1));
p_0(x)=1;

pp = shift(eye(N) * q, 1) + shift(eye(N) * p, -1);
pp(1, :) = eye(1, N);
pp(N, :) = flip(eye(1, N));


printf('State probability after 5 steps\n')
k = 5;
pp ** k


printf('State probability after 20 steps\n')
k = 20;
pp ** k


printf('State probability after 100 steps\n')
k = 100;
p_pract = pp ** k

p_theor_0 = zeros(N, 1);
for x = 1:N
  p_theor_0(x) = ((q / p) ** x - (q / p) ** N) / (1 - (q / p) ** N);
endfor  
printf("P_pract[0]  P_theor[0] Delta\n")
[p_pract(:, 1), p_theor_0, abs(p_pract(1) - p_theor_0)]


# Plot trajectory of wandering
k = 100;
x_end_0_p = zeros(k, 1);
x_end_N_p = zeros(k, 1);
for j = 1:k 
  x_path = zeros(k, 1);
  x_path(1) = round(unifrnd(2, N - 1));
  for i = 2:k
    if x_path(i - 1) == 0 || x_path(i - 1) == N
      x_path(i) = x_path(i - 1);
    else
      rnd = unifrnd(0, 1);
      x_path(i) = (x_path(i - 1) + 1) * (rnd < p) + (x_path(i - 1) - 1) * (rnd >= p);
    endif
    x_end_0_p(i) += 1 * (x_path(i) == 0);
    x_end_N_p(i) += 1 * (x_path(i) == N);
  endfor
endfor
x_end_0_p /= k;
x_end_N_p /= k;


plot(x_path, '--*')

figure
plot(1:k, x_end_0_p, 1:k, x_end_N_p)

