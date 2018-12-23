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
A = [ones(1, n); X]';
B = A' * A;
C = A' * Y';
cn = B^(-1) * C;
cn_matrix = flip(cn')
Yn1 = A * cn;

# cov way
xn = mean(X);
yn = mean(Y);
K = (X - xn)' * (Y - yn) / (n - 1);
b = K / (std(X) ^ 2);
Yn2 = yn + b * (X-xn)';

# using octave functions
m = 1;
cn_octave = polyfit(X, Y, m)
Yn3 = polyval(cn_octave, X);

plot(X, Y, 'b', X, y, 'r', X, Yn1, 'g*--', X, Yn2, 'ko', X, Yn3, 'b.')
legend('Function with noise', 'Unknown function', 
'Linear regression (matrix way)', 'Linear regression (correlation way)', 
'Linear regression (using octave funcs)')


r = Yn1 - Y';
ort = r' * Yn1
sn = sqrt(r' * r / (n - 2));
ss = [s, sn]
