def split_words(data)
  result = []
  data.scan(/[a-z]{2,}/).reject {
    |w| $stop_words.include? w}.each {
      |w| result.push([w, 1]) }
  return result
end

def regroup(pairs_list)
  map = Hash.new{|h,k| h[k] = []}
  pairs_list.each {|pair| pair.each {|p| map[p[0]].push(p)}}
  return map
end

def count_words(map)
  counts = []
  map[1].each {|pair| counts << pair[1] }
  [map[0], counts.reduce(:+)]
end

$stop_words = File.read('../stop_words.txt').split(',')
$data_str = File.read(ARGV[0]).downcase
$nlines = 200

partition = Enumerator.new do |p|
  lines = $data_str.split("\n")
  (0..lines.size-1).step($nlines).each {
    |x| p.yield lines[x..x+$nlines-1].join("\n") }
end

splits = partition.map(&method(:split_words))
splits_by_word = regroup(splits).to_a
word_freqs = splits_by_word.map(&method(:count_words))
word_freqs.sort_by{|x,y|-y}[0..24].each {|w,f| puts "#{w} - #{f}"}