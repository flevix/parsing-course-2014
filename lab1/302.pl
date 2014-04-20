#!/usr/bin/perl
use strict;
use warnings;
 
my $begin = 1;
my $empty = 0;
 
sub c {
    my $s = shift;
    $s =~ s/(<.*?>)//g;
    return $s;
}
 
sub t {
    my $s = shift;
    $s =~ s/^\s+|\s+$//g;
    $s =~ s/\s+/ /g;
    return $s;
}
 
while (<>) {
    $_ = c($_);
    my $curr_empty = /^\s*$/;
    if ($curr_empty) {
        $empty = 1;
    }
    if ($begin and not $curr_empty) {
        $begin = 0;
        $empty = 0;
        print t($_)."\n";
    } elsif (not $begin and not $curr_empty) {
        if ($empty) {
            print "\n";
        }
        $empty = 0;
        print t($_)."\n";
    }
}
