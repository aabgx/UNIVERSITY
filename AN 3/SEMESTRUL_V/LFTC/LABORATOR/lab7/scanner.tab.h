
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     ID = 258,
     DECIMAL = 259,
     REAL_NUMBER = 260,
     HEXA = 261,
     BINARY = 262,
     OCTAL = 263,
     REPETA = 264,
     PANACAND = 265,
     SFREPETA = 266,
     MAIN = 267,
     WHILE = 268,
     USING = 269,
     NAMESPACE = 270,
     STD = 271,
     CIN = 272,
     COUT = 273,
     FOR = 274,
     IF = 275,
     INCLUDE = 276,
     INT = 277,
     DOUBLE = 278,
     IOSTREAM = 279,
     ARROW = 280,
     DECREMENTASSIGN = 281,
     DECREMENT = 282,
     DIVID = 283,
     MOD = 284,
     COUTARROWS = 285,
     LT = 286,
     CINARROWS = 287,
     GT = 288,
     EQUAL = 289,
     NOTEQUAL = 290,
     LE = 291,
     GE = 292,
     AND = 293,
     BITAND = 294,
     OR = 295,
     BITOR = 296,
     NOT = 297,
     INCREMENT = 298,
     INCREMENTASSIGN = 299,
     MULASSIGN = 300,
     DIVIDASSIGN = 301,
     MODASSIGN = 302,
     OPENBRACE = 303,
     CLOSEBRACE = 304,
     OPENPARAN = 305,
     CLOSEPARAN = 306,
     SEMICOLON = 307,
     TWOPOINTS = 308,
     HASHTAG = 309,
     COMMA = 310,
     MUL = 311,
     PLUS = 312,
     MINUS = 313,
     ASSIGN = 314,
     ELSE = 315,
     RETURN = 316,
     POINT = 317,
     STRINGCONSTANT = 318,
     CHAR = 319,
     FLOAT = 320,
     MATHH = 321
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


