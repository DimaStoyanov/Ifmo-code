clc
clear 

a1 = 1;
s1 = 1;

n = 100;
m = 1

x = sort(normrnd(a1, s1, n, m));

F_n = 1 / n : 1 / n : 1;
[a, b] = stairs(x, F_n);

t = (a1 - 3 * s1) : 0.5 : a1 + 3 * s1;
F_real = normcdf(t, a1, s1);

d = 1.36 / sqrt(n);



plot(a,b, t, F_real, a, b - d, a, b + d)