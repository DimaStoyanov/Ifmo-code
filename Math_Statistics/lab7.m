x_min = 0;
x_max = 1;
n = 20;

X = (x_min: (x_max - x_min) / (n - 1) : x_max);
a1 = 3;
a2 = -2;
a3 = 1;
# unknown function
y = a1 + a2 * X + a3 * X .^ 2;

# noise
s = 1.5; 

Z = s  *  normrnd(0, 1, n, 1);
Y = y + Z;

plot(X,y,X,Y)

# recovery of function y(x)
A = [ones(1, n), X, X .^ 2]
B = A' * A;
C = A' * Y;
an = B^(-1) * C;
Yn = A * an;

m = 2;
a1 = polyfit(X, Y, m);
Yn1 = polyval(a1, X);

plot(X, Y, X, y, X, Yn, X, Yn1, 'o')
r = Yn - Y;
r' * Yn
sn = sqrt(r' * r / (n-3))
D = B ^ (-1);
d = diag(D);
t = 1.96;
h = t * sn * d .^ (1/2);
rez = [an - h,an + h]
