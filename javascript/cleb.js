// Draft code to work out Clebsches in JavaScript using BigInts

// As an example, let's work out one of the examples used as a benchmark in the
// CPC paper (P. D. Stevenson, Comput. Phys. Commun. 147, 853 (2002))

// <5/2 1/2 2 0 | 5/2 1/2> = -2*sqrt(2/35)

// Start by defining the input as 2* the true value

j1 = 5n; m1 = 1n; j2 = 4n; m2 = 0n; j=5n; m=1n;

let div = document.createElement('div');
div.innerHTML = "Answer is "+j1;
document.body.append(div);

