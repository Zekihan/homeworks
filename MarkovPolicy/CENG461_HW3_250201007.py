# importing the required module
import matplotlib.pyplot as plt
import random
import time

# get new population given current population and growth rate and max population
def get_new_population(population, r, M):
    if population < M:
        population = round(population * r)
        if population > M:
            population = M
    return population


# Return a numeric reward for this state and action.
def R(s, a):
    return a


# Transition model. From a state and an action, return a list of (result-state, probability) pairs.
def T(s, a, M):
    return [
        (0.2, get_new_population(s - a, 1.00, M)),
        (0.3, get_new_population(s - a, 1.25, M)),
        (0.3, get_new_population(s - a, 1.50, M)),
        (0.2, get_new_population(s - a, 1.75, M)),
    ]


# Solving an MDP by value iteration algorithm.
def value_iteration(mdp, M, gamma=0.9, epsilon=0.001):
    states = list(mdp.keys())
    treshold = epsilon * (1 - gamma) / gamma
    Q1 = dict([((s, a), 0) for s in states for a in mdp[s]])
    while True:
        Q = Q1.copy()
        for s in states:
            for a in mdp[s]:
                Q1[(s, a)] = R(s, a) + gamma * sum(
                    [
                        p1 * max([Q[(s1, a1)] for a1 in mdp[s1]])
                        for (p1, s1) in T(s, a, M)
                    ]
                )
        q_list = list(Q.values())
        q1_list = list(Q1.values())
        delta = max(list(map(lambda x, y: abs(x - y), q_list, q1_list)))
        if delta < treshold:
            return Q


# The expected utility of doing a in state s, according to the Q.
def expected_utility(a, s, Q):
    return Q[(s, a)]


# Given an MDP and a q function Q, determine the best policy, as a mapping from state to action.
def best_policy(mdp, Q):
    states = list(mdp.keys())
    pi = {}
    for s in states:
        pi[s] = max(mdp[s], key=lambda a: expected_utility(a, s, Q))
    return pi


# Solving an MDP by modified policy iteration algorithm.
def modified_policy_iteration(mdp, M, gamma=0.9):
    states = list(mdp.keys())
    Q = dict([((s, a), 0) for s in states for a in mdp[s]])
    pi = dict([(s, 0) for s in states])
    done = True
    while done:
        Q = policy_evaluation(pi, Q, mdp, M, gamma=gamma, k=20)
        for s in states:
            a = max(mdp[s], key=lambda a: expected_utility(a, s, Q))
            if a != pi[s]:
                pi[s] = a
                done = False
    return pi


# Return an updated utility mapping Q from each state in the MDP to its utility, using an approximation.
def policy_evaluation(pi, Q, mdp, M, gamma=0.9, k=20):
    states = list(mdp.keys())
    for i in range(k):
        for s in states:
            for a in mdp[s]:
                Q[(s, a)] = R(s, a) + gamma * sum(
                    [
                        p1 * max([Q[(s1, a1)] for a1 in mdp[s1]])
                        for (p1, s1) in T(s, a, M)
                    ]
                )
    return Q


# Initialization method
def init(M):
    # sample mdp = {state1: [action1, action2], state2: [action1, action2]}
    mdp = {}
    for i in range(1, M + 1):
        actions = []
        for j in range(0, i):
            actions.append(j)
        mdp[i] = actions
    return mdp


# Calculate and return policy and elapsed time for value iteration
def calc_value_iteration(mdp, M, gamma=0.9):

    start = time.time()
    Q = value_iteration(mdp, M, gamma=gamma, epsilon=0.1)
    result = best_policy(mdp, Q)
    end = time.time()
    return result, end - start


# Calculate and return policy and elapsed time for modified policy iteration
def calc_modified_policy_iteration(mdp, M, gamma=0.9):

    start = time.time()
    result = modified_policy_iteration(mdp, M, gamma=gamma)
    end = time.time()
    return result, end - start


# Takes two policies and plots them
def plot_policies(value_iteration_policy, modified_policy_iteration_policy):
    fig, axs = plt.subplots(2, 1, constrained_layout=True)
    fig.suptitle("MDP", fontsize=16)

    axs[0].plot(
        list(value_iteration_policy.keys()), list(value_iteration_policy.values()), "-"
    )
    axs[0].set_title("value iteration")
    axs[0].set_xlabel("fish population")
    axs[0].set_ylabel("fish to catch")

    axs[1].plot(
        list(modified_policy_iteration_policy.keys()),
        list(modified_policy_iteration_policy.values()),
        "-",
    )
    axs[1].set_title("modified policy iteration")
    axs[1].set_xlabel("fish population")
    axs[1].set_ylabel("fish to catch")

    plt.show()


def main():
    M = 100
    discount_factor = 0.9
    mdp = init(M)

    value_iteration_policy, t1 = calc_value_iteration(mdp, M, gamma=discount_factor)
    print("value iteration time elapsed: " + str(t1))

    modified_policy_iteration_policy, t2 = calc_modified_policy_iteration(
        mdp, M, gamma=discount_factor
    )
    print("modified policy iteration time elapsed: " + str(t2))

    plot_policies(value_iteration_policy, modified_policy_iteration_policy)


main()

"""
Sample timings:
    M as 10
        value iteration time elapsed: 0.049999237060546875
        modified policy iteration time elapsed: 0.020000934600830078
    M as 100
        value iteration time elapsed: 13.303086042404175
        modified policy iteration time elapsed: 3.687401056289673
    M as 200
        value iteration time elapsed: 92.65019488334656
        modified policy iteration time elapsed: 23.677830696105957
As we can see from times given above, modified policy iteration takes less time to converge than value iteration
"""


def get_action(state, M):
    return max(0, round(state - M * 0.5725))


"""
When i tried with M as 10 to 200
They all break at some point and go linear.
When calculated avarage breaking point is M * 0.5725885333565304
So i formulated as shown above
It fails for %4 of sample
When it fails it only deviates as 1 from original
"""

"""
If discount factor is 1 value iteration will fail as utility function will keep getting as same amount so it can never overcomes threshold to converge
Although modified policy iteration can calculate a policy but it will be a little bit jumpy(it will have some random values in it) in some parts it will be correct.
As we go toward to 1 it means future is more valuable so it will hunt less and less
Vice versa if we go towards 0 action will be more valuable so it will hunt everything minus one because 0 is not a valid state
"""
