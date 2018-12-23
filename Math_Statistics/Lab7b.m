clc
clear

x_min = -2.2;
x_max = 2.5;
n = 70;

X = x_min: (x_max - x_min) / (n - 1) : x_max;
c1 = 2.8;
c2 = -3.7;
c = [c2, c1]

# unknown function
y = c1 + c2 * X;

# noise
s = 1.5;
Z = s * normrnd(0, 1, 1, n);
Y = y + Z;

# recovery of function y(x)

# matrix way
xn = mean(X);
yn = mean(Y);
K = (X - xn)' * (Y - yn) / (n - 1);
b = K / (std(X) ^ 2);
Yn = yn + b * (X-xn)';

# using octave functions
m = 1;
cn = polyfit(X, Y, m)
Yn1 = polyval(cn, X);

plot(X, Y, 'b', X, y, 'r', X, Yn, 'g*--', X, Yn1, 'po')
#plot(X, Yn, 'g', X, Yn1, 'ro', X, y, 'b')


r = Yn - Y';
ort = r' * Yn
sn = sqrt(r' * r / (n - 2));
ss = [s, sn]
