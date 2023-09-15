#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd $DIR

bash test.sh > ../out/out.txt
bash o1.sh > ../out/out_o1.txt
bash o2.sh > ../out/out_o2.txt
bash o3.sh > ../out/out_o3.txt
bash ofast.sh > ../out/out_ofast.txt

python3 toExcel.py -i ../out/out.txt -o ../tables/out.xlsx
python3 toExcel.py -i ../out/out_o1.txt -o ../tables/out_o1.xlsx
python3 toExcel.py -i ../out/out_o2.txt -o ../tables/out_o2.xlsx
python3 toExcel.py -i ../out/out_o3.txt -o ../tables/out_o3.xlsx
python3 toExcel.py -i ../out/out_ofast.txt -o ../tables/out_ofast.xlsx
