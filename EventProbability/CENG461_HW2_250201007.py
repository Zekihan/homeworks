# imports
import numpy as np

# To make calling easier assing indexes as letters
A = 0
B = 1
C = 2
D = 3
E = 4
F = 5
G = 6

# load data from numpy array file
data = np.load("data 1.npy")

# calculate probability from data directly
def calculate_from_data(query=dict(), evidence=dict()):
    # Evidence string eg. '(data[A] == True) & (data[C] == True)'
    # Query string eg. '(data[A] == True) & (data[C] == True) & (data[D] == True) & (data[G] == False)'

    # construct evidence string
    evidence_string = ""
    for e in evidence.keys():
        evidence_string += f"(data[{e}] == {evidence[e]}) & "
    evidence_string = evidence_string[:-3]

    # construct query string
    query_string = evidence_string + " & "
    if evidence_string == "":
        query_string = ""
        evidence_string = "True"
    for q in query.keys():
        query_string += f"(data[{q}] == {query[q]}) & "
    query_string = query_string[:-3]

    # string are evaluated at runtime
    # the size of data that have query and evidence as their value
    # divided by just evidence
    result = data[D][eval(query_string)].size / data[D][eval(evidence_string)].size
    return result


# normalizes possibilities
def normalize(distribution):
    total = 0
    # add all values
    for value in distribution.values():
        total += value
    # if no value is present just return as is
    if total == 0:
        return distribution
    # divide all values with total
    for key in distribution.keys():
        distribution[key] = distribution[key] / total
    return distribution


# get probability of a node given its value and evidence over a bayes network
def get_probability(node, value, evidence, bayes_network):
    # get parents
    parents = bayes_network[node][0]
    # if node is parent just return its probability
    if len(parents) == 0:
        probability = bayes_network[node][1]["NotConditional"]
    # otherwise get values of parent from evidence
    # and get that value from network
    else:
        parentValues = [evidence[parent] for parent in parents]
        probability = bayes_network[node][1][tuple(parentValues)]
    # if asked value is true return directly
    # otherwise return 1 - probability
    if value == True:
        return probability
    else:
        return 1 - probability


# enumerate all and return true probability of it
def enumerate_all(nodes, evidence, bayes_network):
    # take a copy of nodes since we are going to change it
    nodes_copy = nodes.copy()
    # take a copy of evidence since we are going to change it
    evidence_copy = {key: value for key, value in evidence.items()}
    # if there is no more nodes left return 1
    # base condition for recursive
    if len(nodes_copy) == 0:
        return 1
    # take first node
    Y = nodes_copy.pop()
    # if this node is in evidence use evidence value
    if Y in evidence_copy:
        # multiply probability and and recursive call
        value = get_probability(
            Y, evidence_copy[Y], evidence_copy, bayes_network
        ) * enumerate_all(nodes_copy, evidence_copy, bayes_network)
        return value
    # if this node is not in evidence get both True and False
    else:
        total = 0
        evidence_copy[Y] = True
        # multiply probability and and recursive call
        total += get_probability(Y, True, evidence_copy, bayes_network) * enumerate_all(
            nodes_copy, evidence_copy, bayes_network
        )
        evidence_copy[Y] = False
        # multiply probability and and recursive call
        total += get_probability(
            Y, False, evidence_copy, bayes_network
        ) * enumerate_all(nodes_copy, evidence_copy, bayes_network)
        return total


# create a cartesian product list of lists
def cartesian_product(lists):
    result = [[]]
    for lst in lists:
        result = [x + [y] for x in result for y in lst]
    return result


# calculate inference from bayesian network with enumeration
def enumeration_ask(query, evidence, bayes_network, nodes):
    distribution = {}

    # values are not needed to calculate
    query_to_enumerate = list(query.keys())
    query_length = len(query_to_enumerate)

    # copy evidence since we are going to change it
    evidence_copy = {key: value for key, value in evidence.items()}

    # create a list of values to take cartesion product
    cartesian = []
    for i in range(query_length):
        cartesian.append([False, True])

    # create a cartesion product with all probability of query
    all_probability = cartesian_product(cartesian)
    # enumerate with all probabilities of query
    for probability in all_probability:
        # key_str is for to get asked value later on
        key_str = "{"
        # add query variables with their respective value to evidence
        for j in range(query_length):
            evidence_copy[query_to_enumerate[j]] = probability[j]
            key_str += (
                "'" + str(query_to_enumerate[j]) + "': " + str(probability[j]) + ", "
            )
        key_str = key_str[:-2]
        key_str += "}"
        # add result to distribution
        distribution[key_str] = enumerate_all(nodes, evidence_copy, bayes_network)
    # normalize distribution
    result = normalize(distribution)
    # return the asked probability
    return result[str(query)]


# add boolean values to nodes and return a dictionary
def add_bool(lst):
    result = {}
    for i in lst:
        if "N" in i:
            result[i[1:]] = False
        else:
            result[i] = True
    return result


# takes input
def take_input(nodes):

    # get query input
    print("Please give query variables: ", end="")
    query_string = input()
    # check for empty query
    if query_string == "":
        print("Please give valid input: query cannot be empty")
        return -1
    query_string = " ".join(query_string.split())  # remove extra whitespaces
    query_string = query_string.upper()  # make uppercase so it can accept lowercase too
    query = query_string.split(" ")
    # validate query
    for i in query:
        # query variable has to be in nodes
        if i not in nodes and i[1:] not in nodes:
            if len(i) == 2:
                print(f"Please give valid input: '{i[1:]}' is not a node.")
            else:
                print(f"Please give valid input: '{i}' is not a node.")
            return -1
        # query cannot have a node as positive and negative
        if f"N{i}" in query:
            print(f"Please give valid input: query cannot have both '{i}' and 'n{i}'")
            return -1
        # query cannot have a node twice
        if query.count(i) > 1:
            print(f"Please give valid input: query cannot have '{i}' more than once")
            return -1

    # get evidence input
    print("Please give evidence variables: ", end="")
    evidence_string = input()
    # check for empty query
    if evidence_string == "":
        evidence = []
    else:
        evidence_string = " ".join(evidence_string.split())  # remove extra whitespaces
        evidence_string = (
            evidence_string.upper()
        )  # make uppercase so it can accept lowercase too
        evidence = evidence_string.split(" ")
    # validate evidence
    for i in evidence:
        # evidence variable has to be in nodes
        if i not in nodes and i[1:] not in nodes:
            if len(i) == 2:
                print(f"Please give valid input: '{i[1:]}' is not a node.")
            else:
                print(f"Please give valid input: '{i}' is not a node.")
            return -1
        # evidence cannot have a node as positive and negative
        if f"N{i}" in evidence:
            print(
                f"Please give valid input: evidence cannot have both '{i}' and 'n{i}'"
            )
            return -1
        # evidence cannot have a node twice
        if evidence.count(i) > 1:
            print(f"Please give valid input: evidence cannot have '{i}' more than once")
            return -1
        # evidence and query cannot have same node
        if i in query:
            print(f"Please give valid input: both query and evidence cannot have '{i}'")
            return -1
        # evidence and query cannot have same node
        if f"N{i}" in query:
            print(
                f"Please give valid input: both query and evidence cannot have '{i}' and 'n{i}'"
            )
            return -1
        # evidence and query cannot have same node
        if i[1:] in query:
            print(
                f"Please give valid input: both query and evidence cannot have '{i[1:]}' and 'n{i[1:]}'"
            )
            return -1
    # add boolean values to nodes and return a dictionary
    query_bool = add_bool(query)
    evidence_bool = add_bool(evidence)
    return query_bool, evidence_bool


# get children of node
def get_children(node, bayes_network):
    # recursively get children of a node
    keys = list(bayes_network.keys())
    children = []
    for key in keys:
        if node in bayes_network[key][0]:
            children.extend(get_children(key, bayes_network))
            children.append(key)
    return children


# creating nodes
def create_nodes(bayes_network):
    # for enumeration to work properly it needs all nodes after their children
    # this methods just getting children and children of children and adding to node list.
    keys = list(bayes_network.keys())
    nodes = []
    for key in keys:
        if key not in nodes:
            for child in get_children(key, bayes_network):
                if child not in nodes:
                    nodes.append(child)
            nodes.append(key)
    return nodes


# initialing of nodes and bayes network
def init():
    """
    network is a dictionary
    eg.
    bayes_network = { # only give positive probabilities, since we can get negative part with just 1 - positive
        "node1": [[], {"NotConditional": 1}],  # parent node
        "node2": [  # two parent
            ["node1", "node3"],
            {  # writing all possible combinations of parent nodes
                (True, True): 1,
                (True, False): 1,
                (False, True): 1,
                (False, False): 1,
            },
        ],
        "node3": [  # one parent
            ["D"],
            {
                (True,): 1,
                (False,): 1,
            },  # due to python making (True) and tuple(True) just true it needs that ,
        ],
    }
    """
    bayes_network = {
        "A": [[], {"NotConditional": np.average(data[A])}],
        "B": [[], {"NotConditional": np.average(data[B])}],
        "D": [
            ["A", "B"],
            {
                (True, True): data[D][
                    (data[A] == True) & (data[B] == True) & (data[D] == True)
                ].size
                / data[D][(data[A] == True) & (data[B] == True)].size,
                (True, False): data[D][
                    (data[A] == True) & (data[B] == False) & (data[D] == True)
                ].size
                / data[D][(data[A] == True) & (data[B] == False)].size,
                (False, True): data[D][
                    (data[A] == False) & (data[B] == True) & (data[D] == True)
                ].size
                / data[D][(data[A] == False) & (data[B] == True)].size,
                (False, False): data[D][
                    (data[A] == False) & (data[B] == False) & (data[D] == True)
                ].size
                / data[D][(data[A] == False) & (data[B] == False)].size,
            },
        ],
        "G": [
            ["D"],
            {
                (True,): data[G][(data[D] == True) & (data[G] == True)].size
                / data[G][(data[D] == True)].size,
                (False,): data[G][(data[D] == False) & (data[G] == True)].size
                / data[G][(data[D] == False)].size,
            },
        ],
        "C": [
            ["A"],
            {
                (True,): data[C][(data[A] == True) & (data[C] == True)].size
                / data[G][(data[A] == True)].size,
                (False,): data[C][(data[A] == False) & (data[C] == True)].size
                / data[G][(data[A] == False)].size,
            },
        ],
        "E": [
            ["C"],
            {
                (True,): data[E][(data[C] == True) & (data[E] == True)].size
                / data[G][(data[C] == True)].size,
                (False,): data[E][(data[C] == False) & (data[E] == True)].size
                / data[G][(data[C] == False)].size,
            },
        ],
        "F": [
            ["C"],
            {
                (True,): data[F][(data[C] == True) & (data[F] == True)].size
                / data[G][(data[C] == True)].size,
                (False,): data[F][(data[C] == False) & (data[F] == True)].size
                / data[G][(data[C] == False)].size,
            },
        ],
    }

    # create nodes from network
    nodes = create_nodes(bayes_network)
    return nodes, bayes_network


# start method
def main():

    # initialize nodes and network
    nodes, bayes_network = init()

    # take input from user
    inputs = take_input(nodes)
    if inputs == -1:
        return -1
    query, evidence = inputs

    # calculate probability from bayesian network with enumeration
    from_inference = enumeration_ask(query, evidence, bayes_network, nodes)
    print(f"The probability calculated by inference is {round(from_inference, 4)}.")

    # calculate probability from data
    from_data = calculate_from_data(query, evidence)
    print(f"The probability calculated from data is {round(from_data, 4)}.")

    return 1


main()
