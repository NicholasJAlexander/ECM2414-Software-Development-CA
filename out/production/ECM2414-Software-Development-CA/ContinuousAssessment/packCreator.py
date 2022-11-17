import random

deck = [1]*8+[2]*8+[3]*8+[4]*8
with open("pack.txt", "w") as f:
    for x in random.choices(deck,k=32):
        f.write(str(x)+'\n')