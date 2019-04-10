clc
clear

n = 5;
p = 0.6;
q = 1 - p;

Z = zeros(n+1,1);
Z(1) = p;
Z(n+1) = q;
D = diag(Z);
P1 = diag(p*ones(n,1),1) + diag(q*ones(n,1),-1)+D;
P1(1,2) = p;
P1(n+1,n) = q;
P_ver = P1'

% theor matrix
B = zeros(n+2,1); 
B(1) = 1;
E = eye(n+1); 
P2 = (P_ver - E)';
P2 = [ones(1, columns(P2)); P2];

X = linsolve(P2, B);
P_matrix = fliplr(X')

P = P_ver^500; % lim pract P
p_prak = fliplr(P(end,:))

% theor lim p
m = p/q;
l = 1 : 1 : n+1;
theorP(l) = (m.^(l-1))*((1-m)/(1-(m.^(n+1)))) 
