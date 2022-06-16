# basic-markdown-parser-android
Basic markdown parse script for android


## How to Use
This script helps to **highlight** and **underline** strings that wrapped with special markdown chars. **highlightIndicator**, **underlineIndicator** and **highlightColor** values are parametrical.

```
val markdownString = "hello, ::this:: is an __markdown__ string."
BasicMarkdownParser.parseMarkdown(markdownString)
```

## How to Add New Attributes

New attributes can be added by varying parseMarkdown function.

For example, if **bold** attribute is wanted to be added:

```
val highlightIndicator = "::"
val underlineIndicator = "__"
val boldIndicator = "**"      //new indicator should be added
private const val defaultColorCode = "#654FD3"
```
and

```
when (recurrence.stylingChar) {
                highlightIndicator -> {
                    attributed.setSpan(
                        ForegroundColorSpan(highlightColor),
                        recurrence.startIndex,
                        recurrence.endIndex,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
                underlineIndicator -> {
                    attributed.setSpan(
                        UnderlineSpan(),
                        recurrence.startIndex,
                        recurrence.endIndex,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
                boldIndicator -> {    //new span should be added
                    attributed.setSpan(
                        StyleSpan(Typeface.BOLD),
                        recurrence.startIndex,
                        recurrence.endIndex,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
            }
        }

```

Additions should be made.

After these changes, this function will parse `**` wrapped strings as **bold**.
