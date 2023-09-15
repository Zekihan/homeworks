from transpose import transpose

def print_table(attributes, result, data):
    if(result != 'FAILURE'):
        temp_all = []
        for i in range(len(attributes)):
            temp = []
            for j in range(len(data[i])):
                temp.append(result[f"{attributes[i]}_{j}"])
            temp_all.append(temp)
        data = transpose(temp_all)
        print_str = ""
        for i in attributes:
            print_str += '%-12s' % (i) + "| "
        print_str = print_str[:-2]
        print_str += "\n" + "-"*(12*4+7) + "\n"
        for i in range(len(data)):
            for j in range(len(data[i])):
                print_str += '%-12s' % data[i][j] + "| "
            print_str = print_str[:-2]
            print_str += "\n"
        
        # magic_char = '\033[F'
        # ret_depth = magic_char * print_str.count('\n')
        # print('{}{}'.format(ret_depth, print_str), end='', flush = True)
        print(print_str)
    else:
        print(result)
