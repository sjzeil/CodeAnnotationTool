#include <string>

void keyword (String word)
{
  emit ("<span class='keyword'>" + word + "</span>");
}

private final static int tabSize = 4;

//...
/*...*/

int wslen (String whitespace)
{
  /*+1*/int width = 0;/*-1*/
  int len = whitespace.length();  /**co3*/
  for (int i = 0; i < len; ++i)
    {
      char c = whitespace.charAt(i);
      if (c == '\t')
	width = width / tabSize + tabSize;
      else
	++width;
    }
  return width;
}


void wsp (String whitespace)
{
  int w = wslen(whitespace);
  if (w > 1)
    {
      for (int i = 0; i < w; ++i)
	emit("&nbsp;");
    }
  else
    emit (" ");
}
        // a line started by 8 blanks
	// a line started by a tab

/* another
   comment */
