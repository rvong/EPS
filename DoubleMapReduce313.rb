def split_words(data)
  result = []
  data.scan(/[a-z]{2,}/).reject {
    |w| $stop_words.include? w}.each {
      |w| result.push([w, 1]) }
  return result
end

# 5 Groups, by letter
# a-e => A
# f-j => F
# k-o => K
# p-t => P
# u-z => U
def letter_group(letter)
  case letter.downcase
  when 'a'..'e'
    'A'
  when 'f'..'j'
    'F'
  when 'k'..'o'
    'K'
  when 'p'..'t'
    'P'
  when 'u'..'z'
    'U'
  end
end

def regroup(pairs_list)
  map = Hash.new{|h,k| h[k] = []}
  pairs_list.each {
    |pair| pair.each {
      |p| letter = p.first[0]
      map[letter_group(letter)].push(p)
    }
  }
  return map
end

def count_words(sack, item)
  map = Hash.new(0)
  sack.each{|x| map[x[0]] += x[1]}
  item[1].each {|x| map[x[0]] += x[1]}
  return map.to_a
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
splits_by_word.insert(0, [])  # for reduction

word_freqs = splits_by_word.reduce(&method(:count_words))
word_freqs.sort_by{|x,y|-y}[0..24].each {|w,f| puts "#{w} - #{f}"}
