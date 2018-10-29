clc
clear

a = 1; % их оценки Xmin Xmax
b = 5;

n = 10^6;
m = n^(1/3);
X = sort(unifrnd(a, b, n, 1));
Y = hist(X, m)/n; % это nj
h = (X(n) - X(1)) / m;
Fn = Y/h;

hStep = min(X) : h : max(X);
Fn = [Fn, Fn(end)];
[c, d] = stairs(hStep, Fn);

step = a : 0.06 : b;
y = unifpdf(step, a, b);

%plot(step, y, "r", c, d, "b", step, 0, "g");

lStep = min(X) : h : max(X) - h;
rStep = min(X) + h : h : max(X);

Fl = unifcdf(lStep, a, b);
Fr = unifcdf(rStep, a, b);

p0 = Fr - Fl;

sum(p0)

%Z = zeros(m, 1);
%for i = 1 : 1 : m
%    Z(i) = X(1) + (i - 1) * h;
%end


