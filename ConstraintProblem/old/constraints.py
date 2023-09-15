from printer import print_table


def eq(a, b): return a is not None and b is not None and a == b
def noteq(a, b): return a is not None and b is not None and a != b
def noteqtest(a, b): return a is not None and b is not None and a != b


def eqisone(data, knowlage_base):
    for i in range(len(data.values())):
        if(0):
            print_table(["days", "sailors", "boatTypes", "boats"], data, [
                        [1, 2, 3, 4], [1, 2, 3, 4], [1, 2, 3, 4], [1, 2, 3, 4]])
        for j in range(i + 1, len(data.values())):
            if (eq(list(data.values())[i], list(data.values())[j])):
                return True
    return False


def ifthen(data, knowlage_base):
    rules = knowlage_base['ifthen']
    for k in rules:
        a = list(k.keys())
        for i in range(len(data.values())):
            if(a[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k[a[0]])):
                    if(noteq(data[f"{a[1]}_{list(data.keys())[i].split('_')[-1]}"], k[a[1]])):
                        return True
    return False


def ifthennot(data, knowlage_base):
    rules = knowlage_base['ifthennot']
    for k in rules:
        a = list(k.keys())
        for i in range(len(data.values())):
            if(a[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k[a[0]])):
                    if(eq(data[f"{a[1]}_{list(data.keys())[i].split('_')[-1]}"], k[a[1]])):
                        return True
    return False


def iftheneitheror(data, knowlage_base):
    rules = knowlage_base['iftheneitheror']
    for k in rules:
        a = list(k.keys())
        for i in range(len(data.values())):
            if(a[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k[a[0]])):
                    temp_bool1 = eq(
                        data[f"{a[1]}_{list(data.keys())[i].split('_')[-1]}"], k[a[1]])
                    temp_bool2 = eq(
                        data[f"{a[2]}_{list(data.keys())[i].split('_')[-1]}"], k[a[2]])
                    if(not((temp_bool1 and not temp_bool2) or (not temp_bool1 and temp_bool2))):
                        return True
    return False


def equalplus(data, knowlage_base):
    rules = knowlage_base['equalplus']
    for k in rules:
        a = list(k['left'].keys())
        b = list(k['right'].keys())
        left = -1
        right = -1
        for i in range(len(data.values())):
            if(a[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k['left'][a[0]])):
                    left = int(list(data.values())[
                               int(list(data.keys())[i].split('_')[-1])])
        for i in range(len(data.values())):
            if(b[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k['right'][b[0]])):
                    right = int(list(data.values())[
                                int(list(data.keys())[i].split('_')[-1])])
        if (not (left == -1 or right == -1) and left != (right + int(k['right'][b[1]]))):
            return True
    return False


def greater(data, knowlage_base):
    rules = knowlage_base['greater']
    for k in rules:
        left = -1
        right = -1
        a = list(k['left'].keys())
        for i in range(len(data.values())):
            if(a[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k['left'][a[0]])):
                    left = int(list(data.keys())[i].split('_')[-1])
        b = list(k['right'].keys())
        for i in range(len(data.values())):
            if(b[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k['right'][b[0]])):
                    right = int(list(data.keys())[i].split('_')[-1])
        if (not (left == -1 or right == -1) and left <= right):
            return True
    return False


def hepler(k, j, data):
    rules = k[j]
    for k in rules:
        a = list(k.keys())
        for i in range(len(data.values())):
            if(a[0] in list(data.keys())[i]):
                if(eq(list(data.values())[i], k[a[0]])):
                    if(noteq(data[f"{a[1]}_{list(data.keys())[i].split('_')[-1]}"], k[a[1]])):
                        return True
    return False


def oneof(data, knowlage_base):
    rules = knowlage_base['oneof']

    for k in rules:
        op1 = hepler(k, 'option1', data)
        op2 = hepler(k, 'option2', data)
        if(op1 is None and op2 is None):
            res = False
        elif (op1 is None):
            res = op2
        elif (op2 is None):
            res = op1
        else:
            res = op1 and op2
        if(res):
            return True
        a = 0
    return False
