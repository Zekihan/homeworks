counter = 0

DOMAINS = "DOMAINS"
VARIABLES = "VARIABLES"
CONSTRAINTS = "CONSTRAINTS"
FAILURE = "FAILURE"


def is_complete(assignment):
    return None not in (assignment.values())


def select_unassigned_variable(variables, assignment):
    for var in variables:
        if assignment[var] is None:
            return var


def is_consistent(assignment, constraints, knowlage_base):
    global counter
    counter += 1
    for constraint_violated in constraints:
        if constraint_violated(assignment, knowlage_base):
            return False
    return True


def init_assignment(csp):
    assignment = {}
    for var in csp[VARIABLES]:
        assignment[var] = None
    return assignment


def recursive_backtracking(assignment, csp, knowlage_base):
    if is_complete(assignment):
        return assignment
    var = select_unassigned_variable(csp[VARIABLES], assignment)
    index = list(assignment.keys()).index(var)
    for value in csp[DOMAINS][index]:
        assignment[var] = value
        if is_consistent(assignment, csp[CONSTRAINTS], knowlage_base):
            result = recursive_backtracking(assignment, csp, knowlage_base)
            if result != FAILURE:
                return result
        assignment[var] = None
    return FAILURE
