#lang racket

(define (insert n l cmp (ol '()))
  (cond
    [(empty? l)
     (append ol (list n))]
    [(not (cmp n (first l)))
     (append ol (list n) l)]
    [else (insert n
                  (rest l)
                  cmp
                  (append ol (list (first l)))
                  )] ))

(define (isort l cmp (sl '()))
  (cond
    [(empty? l) sl]
    [else (isort (rest l) cmp (insert (first l) sl cmp))] ))

(define (sorting lst)
  (isort lst string>?)
  )

(define (hasNext lst)
  (cond
    [(empty? lst) #f]
    [else #t]
  ))

(define (next lst)
  (car (sorting lst))
)

(define (iteration lst outLst)
  (cond[(hasNext lst)(iteration (cdr (sorting lst)) (append outLst (list(next lst))))]
       [else (print outLst)])
)

(define (iteration-nice lst)
  (iteration lst '())
)

(iteration-nice '("pencil" "brother" "apple" "phone"))


