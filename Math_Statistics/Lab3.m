clc
clear

a = 1;
b = 1;

n = 10^6;
m = 100;
X = sort(normrnd(a, b, n, 1));
hist(X, m)
Y = hist(X, m) / n;

h = (X(n) - X(1)) / m;
Fn = Y/h;

hStep = min(X) : h : max(X);
Fn = [Fn, Fn(end)];
[c, d] = stairs(hStep, Fn);

y = normpdf(hStep, a, b);

% plot(c,d, "b")
plot(hStep, y, "b", c, d, "r")
%plot(step, y,   "r", c, d, "b", step, 0, "g");

lStep = min(X) : h : max(X) - h;
rStep = min(X) + h : h : max(X);  

Fl = normcdf(lStep, a, b);
Fr = normcdf(rStep, a, b);

p0 = Fr - Fl;

sum(p0)

%Z = zeros(m, 1);
%for i = 1 : 1 : m
%    Z(i) = X(1) + (i - 1) * h;
%end

