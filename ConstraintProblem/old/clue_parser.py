def parse(clues, attributes, data):
    knowlage_base = {
        'ifthen': [],
        'ifthennot': [],
        'iftheneitheror': [],
        'equalplus': [],
        'greater': [],
        'oneof': [],
        'alldifferent': []
    }

    for i in clues:
        if (i[0] == "{"):
            a = i.split(" ")[0].split('{')[1].split('}')[0].split(",")
            for j in range(len(a)):
                for k in range(len(a)):
                    if (j != k):
                        if(a[j].split("=")[0] != a[k].split("=")[0]):
                            knowlage_base['ifthennot'].append({f'{a[j].split("=")[0]}': a[j].split("=")[
                                                              1], f'{a[k].split("=")[0]}': a[k].split("=")[1]})
        elif (i.split(" ")[0] == "one"):
            rule = {'option1': [], 'option2': []}
            d1 = i.split(" ")[-3].split("=")
            d2 = i.split(" ")[-1].split("=")

            c = i.split(" ")[2].split('{')[1].split('}')[0].split(",")
            rule['option1'].append({c[0].split("=")
                                    [0]: c[0].split("=")[1], d1[0]: d1[1]})
            rule['option1'].append({c[1].split("=")
                                    [0]: c[1].split("=")[1], d2[0]: d2[1]})
            rule['option2'].append({c[0].split("=")
                                    [0]: c[0].split("=")[1], d2[0]: d2[1]})
            rule['option2'].append({c[1].split("=")
                                    [0]: c[1].split("=")[1], d1[0]: d1[1]})
            knowlage_base['oneof'].append(rule)
            if(c[0].split("=")[0] != c[1].split("=")[0]):
                knowlage_base['ifthennot'].append({f'{c[0].split("=")[0]}': c[0].split("=")[
                    1], f'{c[1].split("=")[0]}': c[1].split("=")[1]})
            if(d1[0] != d2[0]):
                knowlage_base['ifthennot'].append({d1[0]: d1[1], d2[0]: d2[1]})
        elif (i.split(" ")[0] == "if"):
            if(i.split(" ")[-2] == "not"):
                rule = {}
                rule[f'{i.split(" ")[1].split("=")[0]}'] = i.split(" ")[
                    1].split("=")[1]
                rule[f'{i.split(" ")[-1].split("=")[0]}'] = i.split(" ")[-1].split("=")[1]
                knowlage_base['ifthennot'].append(rule)
            elif(i.split(" ")[-2] == "then"):
                rule = {}
                rule[f'{i.split(" ")[1].split("=")[0]}'] = i.split(" ")[
                    1].split("=")[1]
                rule[f'{i.split(" ")[3].split("=")[0]}'] = i.split(" ")[
                    3].split("=")[1]
                knowlage_base['ifthen'].append(rule)
            elif(i.split(" ")[-2] == "or"):
                rule = {}
                rule[f'{i.split(" ")[1].split("=")[0]}'] = i.split(" ")[
                    1].split("=")[1]
                rule[f'{i.split(" ")[-3].split("=")[0]}'] = i.split(" ")[-3].split("=")[1]
                rule[f'{i.split(" ")[-1].split("=")[0]}'] = i.split(" ")[-1].split("=")[1]
                knowlage_base['iftheneitheror'].append(rule)
        elif ("=" in i.split(" ")):
            if(i.split(" ")[-2] == "+"):
                rule = {'left': {}, 'right': {}}
                temp1 = i.split(" ")[0].split('(')[1].split(')')[0].split("=")
                temp2 = i.split(" ")[2].split('(')[1].split(')')[0].split("=")
                rule['left'][f'{temp1[0]}'] = temp1[1]
                rule['right'][f'{temp2[0]}'] = temp2[1]
                rule['right']['add'] = f'+{i.split(" ")[-1]}'
                knowlage_base['equalplus'].append(rule)
            elif(i.split(" ")[-2] == "-"):
                rule = {'left': {}, 'right': {}}
                temp1 = i.split(" ")[0].split('(')[1].split(')')[0].split("=")
                temp2 = i.split(" ")[2].split('(')[1].split(')')[0].split("=")
                rule['left'][f'{temp1[0]}'] = temp1[1]
                rule['right'][f'{temp2[0]}'] = temp2[1]
                rule['right']['add'] = f'-{i.split(" ")[-1]}'
                knowlage_base['equalplus'].append(rule)
        elif ("<" in i.split(" ")):
            # print("<", end=" : ")
            pass
        elif (">" in i.split(" ")):
            rule = {'left': {}, 'right': {}}
            temp1 = i.split(" ")[0].split('(')[1].split(')')[0].split("=")
            temp2 = i.split(" ")[2].split('(')[1].split(')')[0].split("=")
            rule['left'][f'{temp1[0]}'] = temp1[1]
            rule['right'][f'{temp2[0]}'] = temp2[1]
            knowlage_base['greater'].append(rule)
    return knowlage_base
