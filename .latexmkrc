$recorder = 1;

#generate a pdf version of the document using pdflatex
# $pdf_mode = 1;

# Generate a pdf version (and an xdv version) of the document 
# using xelatex, using the commands specified by the $xelatex and xdvipdfmx variables.
$pdf_mode = 1;

$bibtex_use = 2;
$pdf_update_method = 0;

$clean_ext = "log pdf loe bbl nav out snm vrb nlo ptc synctex.gz run.xml";

$out_dir = "build";
$aux_dir = "build";

add_cus_dep('glo', 'gls', 0, 'makeglo2gls');
sub makeglo2gls {
    system("makeindex -s '$_[0]'.ist -t '$_[0]'.glg -o '$_[0]'.gls '$_[0]'.glo");
}

#errorstop mode (the default), TeX stops at each error and asks for user intervention. 
#batchmode it prints nothing on the terminal, errors are scrolled as if the user hit <return> at #every error, and missing files cause the job to abort. 
#nonstopmode, diagnostic message appear on the terminal but as in batch mode there is no user interaction. 
#scrollmode, TeX only stops for missing files or keyboard input.

$pdflatex='xelatex -synctex=1 -interaction=nonstopmode --shell-escape -halt-on-error %O %S';
# $pdflatex = 'pdflatex -synctex=1 -interaction=nonstopmode --shell-escape -halt-on-error %O %S';
# $pdflatex = 'pdflatex %O %S';

#$pdf_previewer = 'open -a /Applications/Skim.app/Contents/MacOS/Skim';
$pdf_previewer = 'okular';

# Custom dependency and function for nomencl package 
# add_cus_dep( 'nlo', 'nls', 0, 'makenlo2nls' );
# sub makenlo2nls {
# system( "makeindex -s nomencl.ist -o \"$_[0].nls\" \"$_[0].nlo\"" );
# }
