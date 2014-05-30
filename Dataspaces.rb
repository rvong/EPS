require 'thread'
require 'set'

$word_space = Queue.new
$freq_space = Queue.new
$stop_words = File.read('../stop_words.txt').split(',').to_set

File.read(ARGV[0]).downcase.scan(/[a-z]{2,}/).each { |w| $word_space.push(w) }

def process_words
    word_freqs = Hash.new(0)
    until $word_space.empty?
      word = $word_space.pop(true) rescue nil
      word_freqs[word] += 1 if word and !$stop_words.include? word
    end
    $freq_space.push(word_freqs)
end

$workers = []
5.times do
  $workers << Thread.new { process_words }
end
$workers.each{|t| t.join}

$workers.clear

$word_freqs = Hash.new(0)
5.times do
  $workers << Thread.new do
    $freq_space.pop.each { |x,y| $word_freqs[x] += y } 
  end
end
$workers.each{|t| t.join}

$word_freqs.sort_by{|x,y|y*-1}.take(25).each{|w,f| puts w+' - '+f.to_s}
