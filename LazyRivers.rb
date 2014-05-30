require 'set'

lines = Enumerator.new(File.read(ARGV.first).downcase.split('\n'))

all_words = Enumerator.new do |w|
	loop do
		words = lines.next.scan(/[a-z]{2,}/)
		words.each { |r| w.yield r }
	end
end

non_stop_words = Enumerator.new do |s|
	stops = File.read('../stop_words.txt').split(',').to_set
	loop do
		w = all_words.next
		s.yield w if !stops.include? w
	end
end

count_and_sort = Enumerator.new do |s|
	freqs = Hash.new(0)
	i = 1
	loop do
		r = non_stop_words.next
		freqs[r] += 1
		s.yield freqs.sort_by{|x,y| y*-1} if i % 5000 == 0
		i += 1
	end
	s.yield freqs.sort_by{|x,y| y*-1}
end

count_and_sort.each {|h|
	puts '-------------------------------'
	h.take(25).each {|w, f| puts w+' - '+f.to_s}
}
