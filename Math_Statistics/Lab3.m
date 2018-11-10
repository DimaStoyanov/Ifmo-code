clc
clear

a = 1;
b = 2;

n = 10^6;
m = 100;
X = sort(unifrnd(a, b, n, 1));
hist(X, m)
Y = hist(X, m) / n;

h = (X(n) - X(1)) / m;
Fn = Y/h;

hStep = X(1) : h : X(end);
Fn = [Fn, Fn(end)];
[c, d] = stairs(hStep, Fn);

steps = a: 0.005 : b;
y = unifpdf(steps, a, b);
plot(steps, y, "b", c, d, "r", steps, 0)

lStep = X(1) : h : X(end) - h;
rStep = X(1) + h : h : X(end);  

Fl = unifcdf(lStep, a, b);
Fr = unifcdf(rStep, a, b);

p0 = Fr - Fl;

sum = sum(p0)

maxDiff = max(y-d')


