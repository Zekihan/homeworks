:- dynamic counter/1, board/1, moveTable/1, instaMoves/1,f/2.

sameL("A", "A").
sameL("B", "B").
sameL("C", "C").
sameL("D", "D").
sameL("E", "E").
sameL("F", "F").
sameL("G", "G").
sameL("H", "H").

same(X,Y) :- X \== Y, f(X,A),f(Y,B),sameL(A,B).


prepBoard :- retractall(f(_,_)),
	random_permutation([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16],X),
	nth0(0,X,Y1),assert(f(Y1,"A")),
	nth0(1,X,Y2),assert(f(Y2,"A")),
	nth0(2,X,Y3),assert(f(Y3,"B")),
	nth0(3,X,Y4),assert(f(Y4,"B")),
	nth0(4,X,Y5),assert(f(Y5,"C")),
	nth0(5,X,Y6),assert(f(Y6,"C")),
	nth0(6,X,Y7),assert(f(Y7,"D")),
	nth0(7,X,Y8),assert(f(Y8,"D")),
	nth0(8,X,Y9),assert(f(Y9,"E")),
	nth0(9,X,Y10),assert(f(Y10,"E")),
	nth0(10,X,Y11),assert(f(Y11,"F")),
	nth0(11,X,Y12),assert(f(Y12,"F")),
	nth0(12,X,Y13),assert(f(Y13,"G")),
	nth0(13,X,Y14),assert(f(Y14,"G")),
	nth0(14,X,Y15),assert(f(Y15,"H")),
	nth0(15,X,Y16),assert(f(Y16,"H")).


different(A,B) :- \+(same(A,B)).

init :- retractall(counter(_)), assert(counter(0)), prepBoard, initBoard,initMove, retractall(instaMoves(_)).
initBoard :- retractall(board(_)),
	assert(board(1)),assert(board(2)),assert(board(3)),assert(board(4)),
	assert(board(5)),assert(board(6)),assert(board(7)),assert(board(8)),
	assert(board(9)),assert(board(10)),assert(board(11)),assert(board(12)),
	assert(board(13)),assert(board(14)),assert(board(15)),assert(board(16)).

incrementCounter :- counter(V0), retractall(counter(_)), V is V0 + 1, assertz(counter(V)).

initMove :- retractall(moveTable(_)).

all_full :- moveTable(1), moveTable(2), moveTable(3), moveTable(4), moveTable(5), moveTable(6), moveTable(7), moveTable(8),
	moveTable(9), moveTable(10), moveTable(11), moveTable(12), moveTable(13), moveTable(14), moveTable(15), moveTable(16).

move :- write('Please choose first card to flip (1 to 16): '), read(X), assert(instaMoves(X)), cls, printboard,
	write('Please choose the second card: '), read(Y), assert(instaMoves(Y)), cls, printboard,prepNext(X,Y).

clearMove(N) :- retract(instaMoves(N)).

prepNext(X,Y) :- moveTable(X), moveTable(Y), write('You already found that pair. \n').
prepNext(X,Y) :- same(X,Y), assert(moveTable(X)), assert(moveTable(Y)), write('Yay! you found a pair \n').
prepNext(X,Y) :- X == end_of_file, exit; Y == end_of_file, exit.
prepNext(X,Y) :- different(X,Y),clearMove(X),clearMove(Y),incrementCounter, write('Thats a failed attempt. Try again. \n').

printsquare(N) :- moveTable(N),f(N,X), write(' '),write(X),write(' ').
printsquare(N) :- instaMoves(N),f(N,X), write(' '),write(X),write(' ').
printsquare(N) :- board(N), write(' # ').

printboard :-
    printsquare(1), printsquare(2), printsquare(3), printsquare(4), nl,
    printsquare(5), printsquare(6), printsquare(7), printsquare(8), nl,
    printsquare(9), printsquare(10), printsquare(11), printsquare(12), nl,
	printsquare(13), printsquare(14), printsquare(15), printsquare(16), nl.


play :- init, cls, printboard, playing.
playing :- move, respond.
respond :- all_full,counter(C), write('You finished game with '), write(C), write(' failed turns. Enter play. to challenge again.').
respond :- playing.
cls :- write('\e[2J').
exit :- halt.