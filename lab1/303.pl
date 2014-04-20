#!/usr/bin/perl
use strict;
use warnings;
 
sub uniq {
    my %seen;
    return grep { !$seen{$_}++ } @_;
}
 
my @arr;
my $str;
while ($str = <>) {
    while ($str =~ /<a\b[^>]*href=\"(\w{3,10}:)?(\/\/)?(([\w-]+\.)+[\w-]+)[:\/\"\s][^>]*>/g) {
        push(@arr, $3."\n");
    }
}
my @u = uniq(@arr);
print sort @u;
