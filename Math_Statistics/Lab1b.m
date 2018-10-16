clc
clear

a = 0;
b = 10;
t0 = 6;

F = unifcdf(t0, a, b)

g = 0.97;
T = norminv((1 + g) / 2);

n = 10 ^ 4;
m = 10 ^ 2;

y = unifrnd(a, b, n, m);


f = mean(y < t0);


dn = T * sqrt(f .* (1 - f) / n);
Il = f - dn;
Ir = f + dn;

x = 1:1:m;
x1 = 1:0.1:m;

plot(x, Il, "r.-", x, Ir, "g.-", x1, F, "b.-")
grid

l = sum(Il > F) + sum(Ir < F)