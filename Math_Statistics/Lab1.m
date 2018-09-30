# Лабораторная работа №1
# Вариант 17
# Черняков Никита

clc
clear

a = 1;
b = 5;

t0 = 3;

unif = unifcdf(t0, a, b);

g = 0.95;
T = norminv((1 + g) / 2);

n = 10 ^ 2;

m = 100;

x = unifrnd(a, b, n, m);
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