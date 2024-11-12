import os
import dspy
from dotenv import load_dotenv
load_dotenv()

OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")

lm = dspy.LM(model="openai/gpt-4o", api_key=OPENAI_API_KEY)
dspy.configure(lm=lm)
    
class SOSAgentSignature(dspy.Signature):
    """You are playing SOS. Choose the next move (either an 'S' or 'O') that maximizes the chance of forming an 'SOS' in any direction."
    If multiple moves form an SOS, choose the one with the highest impact (e.g., completing multiple 'SOS' at once). 
    If no moves form an SOS, focus on blocking the opponent."""

    board_state = dspy.InputField(desc="current state of the SOS game board")
    move = dspy.OutputField(desc="Coordinates and the letter of the best move, e.g., (0, 2, S).", prefix="Move Index:")

class SOSCoT(dspy.Module):
    def __init__(self):
        super().__init__()
        self.generate_answer = dspy.ChainOfThought(SOSAgentSignature)

    def forward(self, board_state):
        pred = self.generate_answer(board_state=board_state)
        return pred

class SOSManager:
    def __init__(self):
        self.board = []
        self.board_state = ""
        self.load_board()
    
    def occupied_check(self, row, col):
        return self.board[row][col] != "."
    
    def sequence_check(self, board, row, col):
        size = len(board)
        b1 = board
        sequence_list = []
        # check horizontal sequence (left to right)
        if col + 2 < size and b1[row][col] == "S" and b1[row][col + 1] == "O" and b1[row][col + 2] == "S":
            sequence_list.append([[row, col], [row, col + 1], [row, col + 2]])
        # check vertical sequence (top to bottom)
        if row + 2 < size and b1[row][col] == "S" and b1[row + 1][col] == "O" and b1[row + 2][col] == "S":
            sequence_list.append([[row, col], [row + 1, col], [row + 2, col]])
        # check diagonal sequence (top-left to bottom-right)
        if row + 2 < size and col + 2 < size and b1[row][col] == "S" and b1[row + 1][col + 1] == "O" and b1[row + 2][col + 2] == "S":
            sequence_list.append([[row, col], [row + 1, col + 1], [row + 2, col + 2]])
        # check diagonal sequence (top-right to bottom-left)
        if row + 2 < size and col - 2 >= 0 and b1[row][col] == "S" and b1[row + 1][col - 1] == "O" and b1[row + 2][col - 2] == "S":
            sequence_list.append([[row, col], [row + 1, col - 2], [row + 2, col - 2]])
        return sequence_list
    
    def move_check(self, row, col, move):
        sequence_list = []
        b1 = self.board
        size = len(b1)
        b1[row][col] = move
        for i in range(0, size):
            for j in range(0, size):
                score_list = self.sequence_check(b1, i, j)
                for seq in score_list:
                    for c in seq:
                        if c[0] == row and c[1] == col:
                            return True
        return False
    
    def get_board_state(self):
         return self.board_state
    
    def get_board(self):
        return self.board
    
    def load_board(self):
        with open('src\\board_input.txt', "r") as f:
            content = f.readlines()
            for line in content:
                temp = []
                self.board_state += line
                for char in line:
                    if char == "S" or char == "O" or char == ".":
                        temp.append(char)
                self.board.append(temp)

trainset = [dspy.Example(board_state="[. S .]\n[. . O]\n[. S O]", move="(1, 1, O)").with_inputs("board_state"), 
dspy.Example(board_state="[. . .]\n[S O .]\n[. . .]", move="(1, 2, S)").with_inputs("board_state"), 
dspy.Example(board_state="[. . S]\n[. O .]\n[. . .]", move="(2, 0, S)").with_inputs("board_state"), 
dspy.Example(board_state="[S . .]\n[. . .]\n[. . S]", move="(1, 1, O)").with_inputs("board_state"), 
dspy.Example(board_state="[. O S]\n[O O .]\n[S . S]", move="(0, 0, S)").with_inputs("board_state"),
dspy.Example(board_state="[S . .]\n[O S .]\n[O O .]", move="(0, 1, S)").with_inputs("board_state"),
dspy.Example(board_state="[S . S]\n[O . S]\n[S . .]", move="(0, 1, O)").with_inputs("board_state"), 
dspy.Example(board_state="[. . S]\n[S O .]\n[. . O]", move="(1, 2, S)").with_inputs("board_state"), 
dspy.Example(board_state="[. . .]\n[S S O]\n[. O S]", move="(0, 2, S)").with_inputs("board_state"), 
dspy.Example(board_state="[O S .]\n[. . .]\n[O . S]", move="(1, 1, O)").with_inputs("board_state"), 
dspy.Example(board_state="[. . .]\n[O S O]\n[S . .]", move="(0, 0, S)").with_inputs("board_state")]

def main():
    cot = SOSCoT()
    manager = SOSManager()
    pred = cot.forward(board_state=manager.get_board_state())
    with open("src\llm_move.txt", "w") as f:
        f.write(pred.move + "\n")
        f.writelines(pred.reasoning)

if __name__ == "__main__":
    main()