# imports
import os

# Constants
FILE_DIR = os.path.abspath(os.path.dirname(__file__))
VARIABLES = "VARIABLES"
DOMAINS = "DOMAINS"
CONSTRAINTS = "CONSTRAINTS"
FAILURE = "FAILURE"

# Check for comletion of all variables
def is_completed(assignment):
    return None not in (assignment.values())


# Return the next unfilled variable
def select_next_unassigned_variable(variables, assignment):
    for var in variables:
        if assignment[var] is None:
            return var


# Check for every contraint for violation of rules
def check_constraints(assignment, constraints, knowledge_base):
    for constraint_violated in constraints:
        if constraint_violated(assignment, knowledge_base):
            return False
    return True


# Assign every variable with None and place them all in a dictionary
def init_assignment(csp):
    assignment = {}
    for var in csp[VARIABLES]:
        assignment[var] = None
    return assignment


# Do recurvise backtracking to find solution
def recursive_backtracking(assignment, csp, knowledge_base):
    # condition for breaking recursion
    if is_completed(assignment):
        return assignment

    var = select_next_unassigned_variable(csp[VARIABLES], assignment)
    index = list(assignment.keys()).index(var)
    for value in csp[DOMAINS][index]:
        assignment[var] = value
        if check_constraints(assignment, csp[CONSTRAINTS], knowledge_base):
            result = recursive_backtracking(assignment, csp, knowledge_base)
            if result != FAILURE:
                return result
        assignment[var] = None
    return FAILURE


# Reads data and splits all lines
# Returns a tuple of category names and their values
def read_data(path):
    f = open(path)
    lines = f.readlines()
    f.close()
    categories = []
    categories_values = []
    for i in lines:
        category = []
        categories.append(i[:-1].split(",")[0])
        for j in i[:-1].split(",")[1:]:
            category.append(j)
        categories_values.append(category)
    return categories, categories_values


# Reads data and splits all lines
# Returns a list of clues as strings
def read_clues(path):
    f = open(path)
    lines = f.readlines()
    f.close()
    clues = []
    for i in lines:
        clues.append(i[:-1])
    return clues


# Prepare categories and create domain for each variable
def prep_data(categories, data):
    variables = []
    domains = []
    for i in range(len(categories)):
        for j in range(len(data[i])):
            variables.append(f"{categories[i]}_{j}")
            domains.append(data[i])
    return variables, domains


# Takes a number and reads data and clues of that number
# Tries to solve numbered problem
def run_csp(num):
    data_path = os.path.join(FILE_DIR, f"data-{num}.txt")
    clues_path = os.path.join(FILE_DIR, f"clues-{num}.txt")

    categories, data = read_data(data_path)
    clues = read_clues(clues_path)
    knowledge_base = parse(clues, categories, data)

    variables, domains = prep_data(categories, data)
    my_csp = {
        VARIABLES: variables,
        DOMAINS: domains,
        CONSTRAINTS: [
            are_all_same,
            is_satisfies_if_then,
            is_satisfies_if_then_not,
            is_satisfies_if_then_either_or,
            is_satisfies_equality,
            is_satisfies_inequality,
            is_satisfies_one_or_the_other,
        ],
    }

    result = recursive_backtracking(init_assignment(my_csp), my_csp, knowledge_base)
    print_table(categories, result)


# Transpose a list
def transpose(list_transpose):
    return [
        [list_transpose[j][i] for j in range(len(list_transpose))]
        for i in range(len(list_transpose[0]))
    ]


# Print result as a table
def print_table(categories, result):
    print_str = ""
    if result != FAILURE:
        result_table = []
        for category in categories:
            result_column = []
            num = 0
            while True:
                if f"{category}_{num}" in result:
                    result_column.append(result[f"{category}_{num}"])
                    num += 1
                else:
                    break
            result_table.append(result_column)

        # Get max length of every column
        max_lengths = []
        for i in range(len(categories)):
            temp = result_table[i].copy()
            temp.append(categories[i])
            max_lengths.append(len(max(temp, key=len)) + 1)

        result_table = transpose(result_table)
        for i in range(len(categories)):
            print_str += f"%-{max_lengths[i]}s" % (categories[i]) + "| "
        print_str = print_str[:-2]
        print_str += "\n" + "-" * (sum(max_lengths, (len(max_lengths) - 1) * 2)) + "\n"

        for i in range(len(result_table)):
            for j in range(len(result_table[i])):
                print_str += f"%-{max_lengths[j]}s" % result_table[i][j] + "| "
            print_str = print_str[:-2]
            print_str += "\n"
    else:
        print_str += "This data set and clues have no solution."

    print(print_str)


# Parse clues and construct a knowledge base with rules
def parse(clues, categories, data):
    # starting dictionary
    knowledge_base = {
        "ifThen": [],
        "ifThenNot": [],
        "ifThenEitherOr": [],
        "equality": [],
        "inequality": [],
        "oneOrTheOther": [],
        "allDifferent": [],
    }

    for i in clues:
        # {x=a,y=b,z=c} are all different
        # Adds all possible combinations to if then not as it is basically the same
        if i[0] == "{":
            inside_of_curly = i.split(" ")[0].split("{")[1].split("}")[0].split(",")
            inside_length = len(inside_of_curly)
            for j in range(inside_length):
                for k in range(inside_length):
                    subject1 = inside_of_curly[j].split("=")
                    subject2 = inside_of_curly[k].split("=")
                    if (j != k) and (subject1[0] != subject2[0]):
                        knowledge_base["ifThenNot"].append(
                            {subject1[0]: subject1[1], subject2[0]: subject2[1]}
                        )

        # one of {x=a,y=b} corresponds to z=c other t=d
        # adds {option1: [{x: a, z: c}, {y: b, t: d}], option2: [{x: a, t: d}, {y: b, z: c}]}
        elif i.split(" ")[0] == "one":
            rule = {"option1": [], "option2": []}
            zc = i.split(" ")[-3].split("=")
            td = i.split(" ")[-1].split("=")

            inside_of_curly = i.split(" ")[2].split("{")[1].split("}")[0].split(",")
            xa = inside_of_curly[0].split("=")
            yb = inside_of_curly[1].split("=")
            rule["option1"].append({xa[0]: xa[1], zc[0]: zc[1]})
            rule["option1"].append({yb[0]: yb[1], td[0]: td[1]})
            rule["option2"].append({xa[0]: xa[1], td[0]: td[1]})
            rule["option2"].append({yb[0]: yb[1], zc[0]: zc[1]})
            knowledge_base["oneOrTheOther"].append(rule)
            if xa[0] != yb[0]:
                knowledge_base["ifThenNot"].append({xa[0]: xa[1], yb[0]: yb[1]})
            if zc[0] != td[0]:
                knowledge_base["ifThenNot"].append({zc[0]: zc[1], td[0]: td[1]})

        elif i.split(" ")[0] == "if":
            # if x=a then not y=b
            # adds {'x': 'a', 'y': 'b'}
            second_last = i.split(" ")[-2]
            if second_last == "not":
                rule = {}
                first_element = i.split(" ")[1].split("=")
                second_element = i.split(" ")[-1].split("=")
                rule[first_element[0]] = first_element[1]
                rule[second_element[0]] = second_element[1]
                knowledge_base["ifThenNot"].append(rule)
            # if x=a then y=b
            # adds {'x': 'a', 'y': 'b'}
            elif second_last == "then":
                rule = {}
                first_element = i.split(" ")[1].split("=")
                second_element = i.split(" ")[-1].split("=")
                rule[first_element[0]] = first_element[1]
                rule[second_element[0]] = second_element[1]
                knowledge_base["ifThen"].append(rule)
            # if x=a then either y=b or z=c
            # adds {'x': 'a', 'y': 'b', 'z': 'c'}
            elif second_last == "or":
                rule = {}
                first_element = i.split(" ")[1].split("=")
                second_element = i.split(" ")[-3].split("=")
                third_element = i.split(" ")[-1].split("=")
                rule[first_element[0]] = first_element[1]
                rule[second_element[0]] = second_element[1]
                rule[third_element[0]] = third_element[1]
                knowledge_base["ifThenEitherOr"].append(rule)

        # some for of n(x=a) = n(y=b) + m
        # adds {'left': {'x': 'a'}, 'right': {'y': 'b', 'add': '+m'}}
        elif "=" in i.split(" "):
            rule = {"left": {}, "right": {}}
            temp1 = i.split(" ")[0].split("(")[1].split(")")[0].split("=")
            temp2 = i.split(" ")[2].split("(")[1].split(")")[0].split("=")
            rule["left"][temp1[0]] = temp1[1]
            rule["right"][temp2[0]] = temp2[1]
            # n(x=a) = n(y=b) + m
            if i.split(" ")[-2] == "+":
                rule["right"]["add"] = f'+{i.split(" ")[-1]}'
            # n(x=a) = n(y=b) - m
            elif i.split(" ")[-2] == "-":
                rule["right"]["add"] = f'-{i.split(" ")[-1]}'
            # n(x=a) = n(y=b)
            else:
                rule["right"]["add"] = "+0"
            knowledge_base["equality"].append(rule)

        # n(x=a) < n(y=b)
        # {'left': {'y': 'b'}, 'right': {'x': 'a'}}
        elif "<" in i.split(" "):
            rule = {"left": {}, "right": {}}
            temp1 = i.split(" ")[0].split("(")[1].split(")")[0].split("=")
            temp2 = i.split(" ")[2].split("(")[1].split(")")[0].split("=")
            rule["right"][temp1[0]] = temp1[1]
            rule["left"][temp2[0]] = temp2[1]
            knowledge_base["inequality"].append(rule)

        # n(x=a) > n(y=b)
        # {'left': {'x': 'a'}, 'right': {'y': 'b'}}
        elif ">" in i.split(" "):
            rule = {"left": {}, "right": {}}
            temp1 = i.split(" ")[0].split("(")[1].split(")")[0].split("=")
            temp2 = i.split(" ")[2].split("(")[1].split(")")[0].split("=")
            rule["left"][temp1[0]] = temp1[1]
            rule["right"][temp2[0]] = temp2[1]
            knowledge_base["inequality"].append(rule)
    return knowledge_base


# Checks for equal and filled
def is_equal(a, b):
    return a is not None and b is not None and a == b


# Checks for not equal and filled
def is_not_equal(a, b):
    return a is not None and b is not None and a != b


# check for same value in every category
def are_all_same(assignment, knowledge_base):
    assignment_length = len(assignment.values())
    assignment_values = list(assignment.values())
    for i in range(assignment_length):
        for j in range(i + 1, assignment_length):
            if is_equal(assignment_values[i], assignment_values[j]):
                return True
    return False


# Check if satisfies for if then rules
def is_satisfies_if_then(assignment, knowledge_base):
    rules = knowledge_base["ifThen"]
    assignment_values = list(assignment.values())
    assignment_keys = list(assignment.keys())
    assignment_length = len(assignment.values())
    for rule in rules:
        rule_keys = list(rule.keys())
        for i in range(assignment_length):
            if rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], rule[rule_keys[0]]):
                    if is_not_equal(
                        assignment[
                            f"{rule_keys[1]}_{assignment_keys[i].split('_')[-1]}"
                        ],
                        rule[rule_keys[1]],
                    ):
                        return True
    return False


# Check if satisfies for if then not rules
def is_satisfies_if_then_not(assignment, knowledge_base):
    rules = knowledge_base["ifThenNot"]
    assignment_values = list(assignment.values())
    assignment_keys = list(assignment.keys())
    assignment_length = len(assignment.values())
    for rule in rules:
        rule_keys = list(rule.keys())
        for i in range(assignment_length):
            if rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], rule[rule_keys[0]]):
                    if is_equal(
                        assignment[
                            f"{rule_keys[1]}_{assignment_keys[i].split('_')[-1]}"
                        ],
                        rule[rule_keys[1]],
                    ):
                        return True
    return False


# Check if satisfies for if then either or rules
def is_satisfies_if_then_either_or(assignment, knowledge_base):
    rules = knowledge_base["ifThenEitherOr"]
    assignment_values = list(assignment.values())
    assignment_keys = list(assignment.keys())
    assignment_length = len(assignment.values())
    for rule in rules:
        rule_keys = list(rule.keys())
        for i in range(assignment_length):
            if rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], rule[rule_keys[0]]):
                    current_value1 = assignment[
                        f"{rule_keys[1]}_{assignment_keys[i].split('_')[-1]}"
                    ]
                    current_value2 = assignment[
                        f"{rule_keys[2]}_{assignment_keys[i].split('_')[-1]}"
                    ]
                    temp_bool1 = is_equal(current_value1, rule[rule_keys[1]])
                    temp_bool2 = is_equal(current_value2, rule[rule_keys[2]])
                    if current_value1 is not None and current_value2 is not None:
                        if not (
                            (temp_bool1 and not temp_bool2)
                            or (not temp_bool1 and temp_bool2)
                        ):
                            return True
    return False


# Check if satisfies for equality rules
def is_satisfies_equality(assignment, knowledge_base):
    rules = knowledge_base["equality"]
    assignment_values = list(assignment.values())
    assignment_keys = list(assignment.keys())
    assignment_length = len(assignment.values())
    for rule in rules:
        left_rule_keys = list(rule["left"].keys())
        right_rule_keys = list(rule["right"].keys())
        left_numeric_value = -1
        right_numeric_value = -1
        for i in range(assignment_length):
            if left_rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], rule["left"][left_rule_keys[0]]):
                    left_numeric_value = int(
                        assignment_values[int(assignment_keys[i].split("_")[-1])]
                    )
            if right_rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], rule["right"][right_rule_keys[0]]):
                    right_numeric_value = int(
                        assignment_values[int(assignment_keys[i].split("_")[-1])]
                    )
        if not (
            left_numeric_value == -1 or right_numeric_value == -1
        ) and left_numeric_value != (
            right_numeric_value + int(rule["right"][right_rule_keys[1]])
        ):
            return True
    return False


# Check if satisfies for inequality rules
def is_satisfies_inequality(assignment, knowledge_base):
    rules = knowledge_base["inequality"]
    assignment_values = list(assignment.values())
    assignment_keys = list(assignment.keys())
    assignment_length = len(assignment.values())
    for rule in rules:
        left_rule_keys = list(rule["left"].keys())
        right_rule_keys = list(rule["right"].keys())
        left_numeric_value = -1
        right_numeric_value = -1
        for i in range(assignment_length):
            if left_rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], rule["left"][left_rule_keys[0]]):
                    left_numeric_value = int(assignment_keys[i].split("_")[-1])
            if right_rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], rule["right"][right_rule_keys[0]]):
                    right_numeric_value = int(assignment_keys[i].split("_")[-1])
        if (
            not (left_numeric_value == -1 or right_numeric_value == -1)
            and left_numeric_value <= right_numeric_value
        ):
            return True
    return False


# Helper for one or the other rules
# Checks for one option out of two
def is_satisfies_one_or_the_other_helper(rule, option, assignment):
    rule_subset = rule[option]
    assignment_length = len(assignment.values())
    assignment_values = list(assignment.values())
    assignment_keys = list(assignment.keys())
    for sub_rule in rule_subset:
        sub_rule_keys = list(sub_rule.keys())
        for i in range(assignment_length):
            if sub_rule_keys[0] in assignment_keys[i]:
                if is_equal(assignment_values[i], sub_rule[sub_rule_keys[0]]):
                    if is_not_equal(
                        assignment[
                            f"{sub_rule_keys[1]}_{assignment_keys[i].split('_')[-1]}"
                        ],
                        sub_rule[sub_rule_keys[1]],
                    ):
                        return True
    return False


# Check if satisfies for one or the other rules
def is_satisfies_one_or_the_other(assignment, knowledge_base):
    rules = knowledge_base["oneOrTheOther"]
    for rule in rules:
        option1_result = is_satisfies_one_or_the_other_helper(
            rule, "option1", assignment
        )
        option2_result = is_satisfies_one_or_the_other_helper(
            rule, "option2", assignment
        )
        if option1_result is None and option2_result is None:
            res = False
        elif option1_result is None:
            res = option2_result
        elif option2_result is None:
            res = option1_result
        else:
            res = option1_result and option2_result
        if res:
            return True
    return False


# look which problems exists in same directory
def look_up_problems(path):
    clues = []
    data = []
    puzzles = []
    for root, dirs, files in os.walk(FILE_DIR):
        for file in files:
            if "clues-" in file:
                clues.append(file)
            elif "data-" in file:
                data.append(file)
        break
    for clue in clues:
        num = clue.split(".")[0].split("-")[1]
        if clue.replace("clues-", "data-") in data:
            puzzles.append(num)
    return puzzles


# prints available problems and asks for a number
def user_interface(problems):
    if len(problems) > 0:
        print("The problems available in this directory: ", end="")
        for problem in problems:
            print(problem, end=" ")
        print("")

        input_number = input("Choose a problem: ​")
        if input_number in problems:
            print(
                f"\nHere is the solution to the problem defined in data-{input_number}.txt and clues-{input_number}.txt.\n"
            )
            return input_number
        else:
            print("This problem doesn't exists in this directory.")
            exit(0)
    else:
        print(f"There is no available problem in this directory: '{FILE_DIR}'")
        exit(0)


problems = look_up_problems(FILE_DIR)
input_number = user_interface(problems)
run_csp(input_number)