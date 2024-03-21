%{
#include "analyzer.tab.h"
#include <stdio.h>
%}

%option noyywrap

ZERO 0
UNU 1
DOI 2
TREI 3
PATRU 4
CINCI 5
SASE 6
SAPTE 7
OPT 8
NOUA 9
DOUA_PUNCTE :

%%
{ZERO}     {return ZEROO;}
{UNU}	   { return UNUU;}
{DOI}     {return DOII;}
{TREI}	   {return TREII;}
{PATRU}     {return PATRUU;}
{CINCI}	   {return CINCII;}
{SASE}     {return SASEE;}
{SAPTE}	   {return SAPTEE;}
{OPT}     {return OPTT;}
{NOUA}	   {return NOUAA;}
{DOUA_PUNCTE}     {return DOUA_PUNCTEE;}
[ ]       /* ignore spaces */
"."       /* ignore punctuation */
.         /* ignore invalid caracters */
%%