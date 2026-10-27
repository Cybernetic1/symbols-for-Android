from PIL import Image, ImageDraw, ImageFont
import os

# 1. Create or open an image
img = Image.new('RGB', (500, 300), color=(255, 255, 255))
draw = ImageDraw.Draw(img)

# 2. Define your font file and the desired font size (e.g., 40)
font_candidates = [
	'/usr/share/fonts/truetype/dejavu/DejaVuSerif-Bold.ttf',
	'/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf',
	'/usr/share/fonts/truetype/liberation2/LiberationSerif-Bold.ttf',
	'/usr/share/fonts/truetype/noto/NotoSans-Regular.ttf',
	]
font = None
font_size = 80
for path in font_candidates:
	if os.path.exists(path):
		font = ImageFont.truetype(path, size=int(font_size * 0.75))
		print("Font=", font.getname())
		break
	if font is None:
		font = ImageFont.load_default()
		print("No font!")

# 3. Load the font with the specific size
# font = ImageFont.truetype(font_path, font_size)

# 4. Draw the text using the configured font object
draw.text((50, 100), "Hello, π !", font=font, fill=(0, 0, 0))

# 5. Save or show the image
img.save("output.png")
img.show()
