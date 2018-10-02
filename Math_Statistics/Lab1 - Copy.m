
clc
clear

l=3;

t0 = 0.5;

unif = expcdf(t0, l)

g = 0.95;
T = norminv((1 + g) / 2);

n = 10 ^ 4;

m = 100;

x = exprnd(l, n, m);
x1 = 0:0.05:m;

rate = mean(x < t0);

d1n = std(x < t0) * T / sqrt(n);
dn = T * sqrt(rate .* (1 - rate) / n);
Il = rate - dn;
Ir = rate + dn;
t = 1:1:m; 

sch = sum(Il > unif) + sum(Ir < unif)

plot(t, Il, "r*-", t, Ir, "g*-", x1, unif, "b.-")
grid
