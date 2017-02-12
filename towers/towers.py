
def move(srcStack, dstStack):
    print('Before move:', srcStack, ',', dstStack)
    top = srcStack.pop(0)
    assert len(dstStack) == 0 or dstStack[0] > top
    dstStack.insert(0, top)
    print('After move:', srcStack, ',', dstStack)
    
    

def move_stack(stacks, size, src, dst, oth):
    if size == 1:
        move(stacks[src], stacks[dst])
    else:
        move_stack(stacks, size-1, src, oth, dst)
        move(stacks[src], stacks[dst])
        move_stack(stacks, size-1, oth, dst, src)

def show_stacks(stacks):
    for num, stack in enumerate(stacks):
        print('Stack', num, ':', stack) 

def init(stacks, num, src):
    for i in range(3):
        stacks.append([])
    stack = stacks[src]
    for i in range(num):
        stack.append(i+1)

stacks = []
src = 0
num = 5
init(stacks, num, src)
show_stacks(stacks)
move_stack(stacks, num, src, 2, 1)
show_stacks(stacks)
