import xlsxwriter 
import os


my_path = os.path.abspath(os.path.dirname(__file__))
path = os.path.join(my_path, "out.txt")

f = open(path,"r")
a = f.read()
f.close()

x = a.split("--------------------------------------------------------------\n")
while (x.count("")): 
    x.remove("")  
index = 1
data=[]
for i in range(0,len(x),4):
	
    if(x[i] != ""):
        row =[]
        x1 = x[i].split(" ")
        cacheSize = int(x1[2])
        blockSize = int(x1[3])
        assoc = int(x1[4])
        arraySize = int(x1[6])
        x2 = x[i+1].split("\n")
        elapsed = float(x2[0].split(": ")[1])
        elapsedPin = float(x2[1].split(": ")[1])

        load = float(x2[6].split(" ")[-1].split("%")[0])
        store = float(x2[11].split(" ")[-1].split("%")[0])
        total = float(x2[16].split(" ")[-1].split("%")[0])

        x2 = x[i+3].split("\n")
        modElapsed = float(x2[0].split(": ")[1])
        modElapsedPin = float(x2[1].split(": ")[1])

        modLoad = float(x2[6].split(" ")[-1].split("%")[0])
        modStore = float(x2[11].split(" ")[-1].split("%")[0])
        modTotal = float(x2[16].split(" ")[-1].split("%")[0])

        row.append(index) 
        row.append(cacheSize) 
        row.append(blockSize) 
        row.append(assoc) 
        row.append(arraySize) 

        row.append(elapsed) 
        row.append(modElapsed) 
        row.append( elapsedPin) 
        row.append( modElapsedPin) 

        row.append(load/100) 
        row.append(modLoad/100) 
        row.append(store/100) 
        row.append(modStore/100) 
        row.append(total/100) 
        row.append(modTotal/100)
        index += 1
        data.append(row)

workbook = xlsxwriter.Workbook(os.path.join(my_path, "out.xlsx")) 
worksheet = workbook.add_worksheet() 

cell_format1 = workbook.add_format()
cell_format1.set_num_format(1)
cell_format1.set_align('center')
cell_format1.set_align('vcenter')
cell_format2 = workbook.add_format()
cell_format2.set_num_format('0.000000')
cell_format2.set_align('center')
cell_format2.set_align('vcenter')
cell_format3 = workbook.add_format()
cell_format3.set_num_format(10)
cell_format3.set_align('center')
cell_format3.set_align('vcenter')

options = {'data': data,
           'columns': [{'header': 'index',
                        'format': cell_format1,
                        },
						{'header': 'cache size',
                        'format': cell_format1,
                        },
						{'header': 'block size',
                        'format': cell_format1,
                        },
						{'header': 'assoc',
                        'format': cell_format1,
                        },
						{'header': 'array size',
                        'format': cell_format1,
                        },
						{'header': 'elapsed',
                        'format': cell_format2,
                        },
						{'header': 'mod-elapsed',
                        'format': cell_format2,
                        },
						{'header': 'elapsed-pin',
                        'format': cell_format2,
                        },
						{'header': 'mod-elapsed-pin',
                        'format': cell_format2,
                        },
						{'header': 'load',
                        'format': cell_format3,
                        },
						{'header': 'mod-load',
                        'format': cell_format3,
                        },
						{'header': 'store',
                        'format': cell_format3,
                        },
						{'header': 'mod-store',
                        'format': cell_format3,
                        },
						{'header': 'total',
                        'format': cell_format3,
                        },
						{'header': 'mod-total',
                        'format': cell_format3,
                        },
						{'header': 'time diff',
                        'formula': '=[@[mod-elapsed]]-[@elapsed]',
						'format': cell_format2,
                        },
						{'header': 'load diff',
                        'formula': '=[@load]-[@[mod-load]]',
						'format': cell_format3,
                        },
						{'header': 'store diff',
                        'formula': '=[@store]-[@[mod-store]]',
						'format': cell_format3,
                        },
						{'header': 'total diff',
                        'formula': '=[@total]-[@[mod-total]]',
						'format': cell_format3,
                        },
                       ]}
worksheet.add_table(f'A1:S{index}', options)
workbook.close() 