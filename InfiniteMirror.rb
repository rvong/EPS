require('set')
rec_limit = 5000

def count(word_list, stopwords, wordfreqs)
	return if word_list == []
	word = word_list.first
	wordfreqs[word] += 1 if !stopwords.include? word
	count(word_list.drop(1), stopwords, wordfreqs)
end

def wf_print(wordfreqs)
	return if wordfreqs == []
	(w, f) = wordfreqs.first
	puts "#{w} - #{f}"
	wf_print(wordfreqs.drop(1))
end

stop_words = File.read('../stop_words.txt').split(',').to_set
words = File.read(ARGV[0]).downcase.scan(/[a-z]{2,}/)
word_freqs = Hash.new(0)
for i in (0..words.size).step(rec_limit)
	count(words.slice(i, rec_limit), stop_words, word_freqs)
end
wf_print(word_freqs.sort_by{|x,y|y*-1}.take(25))
