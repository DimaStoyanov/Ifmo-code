clc
clear

x_min = -2.2;
x_max = 2.5;
n = 70;

X = (x_min: (x_max - x_min) / (n - 1) : x_max);
a1 = 1.7;
a2 = -2.4;
a3 = -3.6;
a = [a3, a2, a1]

# unknown function
y = a1 + a2 * X + a3 * X .^ 2;

# noise
s = 1.5; 
Z = s  *  normrnd(0, 1, 1, n);
Y = y + Z;

plot(X, y, X,Y)



# recovery of function y(x)
A = [ones(1, n); X; X .^ 2]';
B = A' * A;
C = A' * Y';
an = B^(-1) * C;
an1 = flip(an')
Yn = A * an;

m = 2;
a11 = polyfit(X, Y, m)
Yn1 = polyval(a11, X);

plot(X, Y, 'b', X, y, 'r', X, Yn, 'g*--', X, Yn1, 'po')



r = Yn - Y';
ort = r' * Yn
ssn = 1.5;
sn = sqrt(r' * r / (n-3));
ss1 = [ssn, sn]
