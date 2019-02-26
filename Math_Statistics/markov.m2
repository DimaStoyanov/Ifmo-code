clc
clear

p = 0.3;
q = 1 - p;
N = 5;


p_0 = zeros(N, 1);
x = round(unifrnd(2, N));
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
pp ** k

printf("Start state\n")
x

p_theor_0 = ((q / p) ** x - (q / p) ** N) / (1 - (q / p) ** N)
p_theor_N = ((q / p) ** x - 1) / ((q / p) ** N - 1)
