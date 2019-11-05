import argparse
import time
from PIL import Image

if __name__=="__main__":
	
	parser = argparse.ArgumentParser()
	parser.add_argument("ImgFileName",type=str,help="Please input the file path")
	args = parser.parse_args()

	filePath = args.ImgFileName
	im = Image.open(filePath)
	 
	if(im.size[0] < 500):
		print("normal")
	else:
		print("abnormal")
