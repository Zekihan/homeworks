import xlsxwriter
import os
import sys
import getopt


def main(argv):
    inputfile = ''
    outputfile = ''
    try:
        opts, args = getopt.getopt(argv, "hi:o:", ["ifile=", "ofile="])
    except getopt.GetoptError:
        print('test.py -i <inputfile> -o <outputfile>')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('test.py -i <inputfile> -o <outputfile>')
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-o", "--ofile"):
            outputfile = arg
    if(inputfile == '' or outputfile == ''):
        print('test.py -i <inputfile> -o <outputfile>')
        sys.exit()

    my_path = os.path.abspath(os.path.dirname(__file__))
    path = os.path.join(my_path, inputfile)

    f = open(path, "r")
    a = f.read()
    f.close()

    x = a.split("------------------------------------\n")
    while(x.count("")):
        x.remove("")

    index = 1
    data = []
    for i in range(0, len(x)):

        if(x[i] != ""):
            row = []
            x1 = x[i].split("\n")
            print(x1)
            mixed = float(x1[1].split(": ")[1])
            mixed_optimized = float(x1[2].split(": ")[1])
            poly = float(x1[3].split(": ")[1])
            poly_optimized = float(x1[4].split(": ")[1])
            horner = float(x1[5].split(": ")[1])
            horner_optimized = float(x1[6].split(": ")[1])

            row.append(index)
            row.append(mixed)
            row.append(mixed_optimized)
            row.append(poly)
            row.append(poly_optimized)

            row.append(horner)
            row.append(horner_optimized)
            index += 1
            data.append(row)

    print(data)

    workbook = xlsxwriter.Workbook(os.path.join(my_path, outputfile))
    worksheet = workbook.add_worksheet()

    cell_format1 = workbook.add_format()
    cell_format1.set_num_format(1)
    cell_format1.set_align('center')
    cell_format1.set_align('vcenter')
    cell_format2 = workbook.add_format()
    cell_format2.set_num_format('0.000000')
    cell_format2.set_align('center')
    cell_format2.set_align('vcenter')

    options = {'data': data,
               'columns': [{'header': 'index',
                            'format': cell_format1,
                            },
                           {'header': 'mixed',
                            'format': cell_format2,
                            },
                           {'header': 'mixed optimized',
                            'format': cell_format2,
                            },
                           {'header': 'poly',
                            'format': cell_format2,
                            },
                           {'header': 'poly optimized',
                            'format': cell_format2,
                            },
                           {'header': 'horner',
                            'format': cell_format2,
                            },
                           {'header': 'horner optimized',
                            'format': cell_format2,
                            }]}
    worksheet.add_table(f'A1:G{index}', options)
    workbook.close()


if __name__ == "__main__":
    main(sys.argv[1:])
