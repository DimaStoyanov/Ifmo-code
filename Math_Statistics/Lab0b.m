clc
clear


g = 0.95;
n = 10 ^ 4;
T = norminv((1 + g) / 2);


fprintf('\nCalculate for abs(x)*exp(-(x-2)^2/3) from -inf to inf\n\n')
a = 2;
s = sqrt(3/2);
x = normrnd(a, s, n, 1);
y = sqrt(3 * pi) * abs(x);
I = mean(y)
d = T * std(y) / sqrt(n)
Int = [I - d, I + d]

n = 10 ^ 6;
a = 2;
s = sqrt(3/2);
x = normrnd(a, s, n, 1);
y = sqrt(3 * pi) * abs(x);
I = mean(y)
d = T * std(y) / sqrt(n)
Int = [I - d, I + d]

format long
I_real = quad('abs(x)*exp(-(x-2)^2/3)', -inf, inf)



fprintf('\n\nCalculate for (x+x.^2).^(1/3) from 1 to 3\n\n')
n = 10 ^ 4;
x = unifrnd(1, 3, n, 5);
y = 2*(x+x.^2).^(1/3);
I = mean(y)
I1 = mean(I)
d = T * std(y) / sqrt(n);
d1 = mean(d)
Int = [I1 - d1, I1 + d1]

n = 10 ^ 6;
x = unifrnd(1, 3, n, 1);
y = 2*(x+x.^2).^(1/3);
I = mean(y)
d = T * std(y) / sqrt(n)
Int = [I - d, I + d]

I_real = quad('(x+x.^2).^(1/3)', 1, 3)