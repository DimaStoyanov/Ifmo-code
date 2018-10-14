clc
clear

l= 1;
t0 = 1;

F = expcdf(t0, l)

g = 0.97;
T = norminv((1 + g) / 2);

n = 10 ^ 4;
m = 10 ^ 2;

y = exprnd(l, n, m);


f = mean(y < t0);


dn = T * sqrt(f .* (1 - f) / n);
Il = f - dn;
Ir = f + dn;

x = 1:1:m;
x1 = 1:0.1:m;

plot(x, Il, "r.-", x, Ir, "g.-", x1, F, "b.-")
grid

l = sum(Il > F) + sum(Ir < F)