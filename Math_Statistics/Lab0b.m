clc
clear


g = 0.95;
n = 10 ^ 6;
T = norminv((1 + g) / 2);


fprintf('\nCalculate for abs(x)*exp(-(x-2)^2/3) from -inf to inf\n\n')
a = 2;
s = sqrt(3/2);
x = normrnd(a, s, n, 1);
y = sqrt(3 * pi) * abs(x);
int = mean(y)
d = T * std(y) / sqrt(n)
I = [int - d; int + d]

format long
I = quad('abs(x)*exp(-(x-2)^2/3)', -inf, inf)



fprintf('\n\nCalculate for x^3*exp(-x/3) from 0 to inf\n\n')
l = 1/3;
x = exprnd(l, n, 1);
y = 3 * x.^3;
int = mean(y)
d = T * std(y) / sqrt(n)
I = [int - d; int + d]

I = quad('x.^3*exp(-x/3)', 0, inf)


fprintf('\n\nCalculate for \n\n')
x = unifrnd(1, 3, n, 1);
y = 2*(x+x.^2).^(1/3);
int = mean(y)
d = T * std(y) / sqrt(n)
I = [int - d; int + d]

I = quad('(x+x.^2).^(1/3)', 1, 3)
