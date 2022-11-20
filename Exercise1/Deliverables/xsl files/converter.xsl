<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
	  <html>
	  <body>
		<h1>Schedule</h1>
		<table border="1">
		  <tr bgcolor="#C83D95">
			<th>Title</th>
			<th>Professor</th>
			<th>Day</th>
		  </tr>
		  <xsl:for-each select='Schedule/Lesson/Lecture[Day="Monday"]/..'>
			<tr bgcolor="#5B2071">
			  <td><xsl:value-of select="Title"/></td>
			  <td><xsl:value-of select="Professor"/></td>
			   <td><xsl:value-of select='Lecture[Day="Monday"]/Day'/></td>
			</tr>
		  </xsl:for-each>
		  <xsl:for-each select='Schedule/Lesson/Lecture[Day="Tuesday"]/..'>
			<tr bgcolor="#ADD8E6">
			  <td><xsl:value-of select="Title"/></td>
			  <td><xsl:value-of select="Professor"/></td>
			   <td><xsl:value-of select='Lecture[Day="Tuesday"]/Day'/></td>
			</tr>
		  </xsl:for-each>
		   <xsl:for-each select='Schedule/Lesson/Lecture[Day="Wednesday"]/..'>
			<tr bgcolor="#D3004C">
			  <td><xsl:value-of select="Title"/></td>
			  <td><xsl:value-of select="Professor"/></td>
			  <td><xsl:value-of select='Lecture[Day="Wednesday"]/Day'/></td>
			</tr>
		  </xsl:for-each>
		   <xsl:for-each select='Schedule/Lesson/Lecture[Day="Thursday"]/..'>
			<tr bgcolor="#00B2D9">
			  <td><xsl:value-of select="Title"/></td>
			  <td><xsl:value-of select="Professor"/></td>
			  <td><xsl:value-of select='Lecture[Day="Thursday"]/Day'/></td>
			</tr>
		  </xsl:for-each>
		  <xsl:for-each select='Schedule/Lesson/Lecture[Day="Friday"]/..'>
			<tr bgcolor="#234990">
			  <td><xsl:value-of select="Title"/></td>
			  <td><xsl:value-of select="Professor"/></td>
			   <td><xsl:value-of select='Lecture[Day="Friday"]/Day'/></td>
			</tr>
		  </xsl:for-each>
		</table>
	  </body>
	  </html>
	</xsl:template>
</xsl:stylesheet> 