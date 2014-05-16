require('set')

stop_words_obj = {
	'stop_words' => Set.new,
	'init' => lambda {stop_words_obj['stop_words'] = File.read('../stop_words.txt').split(',').to_set},
	'is_stop_word' => lambda {|w| stop_words_obj['stop_words'].include? w}
}

data_storage_obj = {
	'data' => [],
	'init' => lambda {|p| data_storage_obj['data'] = File.read(p).downcase.scan(/[a-z]{2,}/)},
	'words' => lambda {return data_storage_obj['data']}
} 

word_freqs_obj = {
	'freqs' => Hash.new(0),
	'incr_count' => lambda {|w| word_freqs_obj['freqs'][w] += 1},
	'sorted' => lambda {return word_freqs_obj['freqs'].sort_by{|x,y|y*-1}},
	'top25' => lambda {return word_freqs_obj['sorted'].call().take(25).each{|w,f| puts w+' - '+f.to_s}}
}

stop_words_obj['init'].call()
data_storage_obj['init'].call(ARGV.first)

data_storage_obj['words'].call().each {|w| 
	word_freqs_obj['incr_count'].call(w) if not stop_words_obj['is_stop_word'].call(w)
}

word_freqs_obj['top25'].call()
