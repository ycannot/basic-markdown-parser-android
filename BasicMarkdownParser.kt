/**
 * @author Yiğit Can Yılmaz (ycannot, https://github.com/ycannot)
 * @date 14.06.2022
 */

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.core.text.toSpannable

object BasicMarkdownParser {
    val highlightIndicator = "::"
    val underlineIndicator = "__"
    private const val defaultColorCode = "#654FD3"

    @ColorInt
    var highlightColor = Color.parseColor(defaultColorCode)
    private val specialCharset = arrayOf(highlightIndicator, underlineIndicator)


    fun parseMarkdown(s: String): Spannable {
        var temp = s
        val recurrences = arrayListOf<Recurrence>()
        specialCharset.forEach { specialChar ->
            temp = temp.replace(specialChar, "")
            recurrences.addAll(extractRecurrences(s, specialChar))
        }
        val attributed = SpannableStringBuilder(temp)
        recurrences.forEach { recurrence ->

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
            }
        }

        return attributed.toSpannable()
    }

    private fun extractRecurrences(
        s: String,
        specialChar: String
    ): ArrayList<Recurrence> {
        val regex = Regex("\\$specialChar(.*?)\\$specialChar")
        return regex.findAll(s).mapTo(arrayListOf()) { match ->
            val startIndex = calculateRefinedStartIndex(s, match)
            val endIndex = startIndex + match.value.length
            specialCharset.forEach {
                endIndex -= match.value.count(it)
            }
            Recurrence(
                match.value,
                startIndex,
                endIndex,
                specialChar
            )
        }

    }

    private fun calculateRefinedStartIndex(
        s: String,
        recurrence: MatchResult
    ): Int {
        val rawIndex = recurrence.range.first
        var specialCharCountBefore = 0
        specialCharset.forEach {
            specialCharCountBefore += s.substring(0, rawIndex).count(it)
        }
        return rawIndex - specialCharCountBefore
    }
    
    fun String.count(s: String): Int{
    return this.split(s).size - 1
    }

    @Keep
    private data class Recurrence(
        val recurrence: String,
        val startIndex: Int,
        val endIndex: Int,
        val stylingChar: String
    )

}
