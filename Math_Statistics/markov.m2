p = 0.3;
q = 1 - p;
N = 5;


p_0 = zeros(N, 1);
p_0(round(unifrnd(2, N)))=1;

p = shift(eye(N) * p, 1) + shift(eye(N) * q, -1);
p(1, :) = eye(1, N);
p(N, :) = flip(eye(1, N));


printf('State probability after 5 steps\n')
k = 5;
p ** k


printf('State probability after 20 steps\n')
k = 20;
p ** k


printf('State probability after 10 steps\n')
k = 100;
p ** k
