import os
from clue_parser import parse
import basic_csp
import constraints
from printer import print_table

exec_dir = os.path.abspath(os.path.dirname(__file__))


def read_data(path):
    f = open(path)
    lines = f.readlines()
    f.close()
    attributes = []
    attributes_values = []
    for i in lines:
        attribute = []
        attributes.append(i[:-1].split(",")[0])
        for j in i[:-1].split(",")[1:]:
            attribute.append(j)
        attributes_values.append(attribute)
    return attributes, attributes_values


def read_clues(path):
    f = open(path)
    lines = f.readlines()
    f.close()
    clues = []
    for i in lines:
        clues.append(i[:-1])
    return clues


def prep_data(attributes, data):
    variables = []
    domains = []
    for i in range(len(attributes)):
        for j in range(len(data[i])):
            variables.append(f"{attributes[i]}_{j}")
            domains.append(data[i])
    return variables, domains


def run_file(num):
    data_path = os.path.join(exec_dir, "input", f"data-{num}.txt")
    clues_path = os.path.join(exec_dir, "input", f"clues-{num}.txt")

    attributes, data = read_data(data_path)
    clues = read_clues(clues_path)
    knowlage_base = parse(clues, attributes, data)

    variables, domains = prep_data(attributes, data)
    my_csp = {basic_csp.VARIABLES: variables,
              basic_csp.DOMAINS: domains,
              basic_csp.CONSTRAINTS: [constraints.eqisone, constraints.ifthen, constraints.ifthennot, constraints.iftheneitheror, constraints.equalplus, constraints.greater, constraints.oneof]}

    result = basic_csp.recursive_backtracking(
        basic_csp.init_assignment(my_csp), my_csp, knowlage_base)
    print_table(attributes, result, data)


run_file(1)
run_file(2)
run_file(3)
