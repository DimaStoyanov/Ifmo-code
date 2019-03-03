clc
clear

p = 0.1
q = 1 - p;
N = 4;

pp = shift(eye(N) * q, 1) + shift(eye(N) * p, -1);
pp(1, : ) = zeros(1, N);
pp(1, 1) = q;
pp(1, 2) = p;
pp(N, :) = zeros(1, N);
pp(N, N - 1) = q;
pp(N, N) = p;


printf('State probability after 5 steps\n')
k = 5;
pp ** k


printf('State probability after 20 steps\n')
k = 20;
pp ** k


printf('State probability after 100 steps\n')
k = 100;
p_pract = pp ** k

p_theor = zeros(N, 1);
for i = 1:N
  p_theor(i) = (p / q) ** (i - 1) * (1 - p / q ) / (1 - (p / q) ** (N + 1));
endfor  
printf("  P_pract      P_theor     Delta\n")
[mean(p_pract)', p_theor, abs(mean(p_pract)' - p_theor)]


# Plot trajectory of wandering
k = 20;
x_end = zeros(k, N);
for j = 1:k 
  x_path = zeros(k, 1);
  x_path(1) = round(unifrnd(2, N - 1));
  for i = 2:k
    rnd = unifrnd(0, 1);
    if x_path(i - 1) == 1
      x_path(i) = (rnd < p) * 2 + (rnd >= p) * 1;
    elseif x_path(i - 1) == N
      x_path(i) = (rnd < p) * N + (rnd >= p) * (N - 1);
    else
      x_path(i) = (x_path(i - 1) + 1) * (rnd < p) + (x_path(i - 1) - 1) * (rnd >= p);
    endif
    x_end(i, x_path(i)) += 1;
  endfor
endfor
x_end /= k;


plot(x_path, '--*')

figure
plot(1:k, x_end)

