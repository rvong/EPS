class WordFrequencyModel
  def initialize(path)
    self.update(path)
  end
  
  def update(path)
    @h = Hash.new(0)
    @@s = File.read('../stop_words.txt').split(',')
    begin
      File.read(path).downcase.scan(/[a-z]{2,}/)
          .reject{|w| @@s.include? w}.each{|w| @h[w] += 1}
    rescue => err
      puts "Exception: #{err}"
      err
    end
  end
end

class WordFrequencyView
  def initialize(model)
    @model = model
  end
  
  def render
    h = @model.instance_variable_get(:@h)
    h.sort_by{|x,y|-y}.take(25)
     .each{|w,f| puts w+' - '+f.to_s}
  end
end

class WordFrequencyController
  def initialize(model, view)
    @model, @view = model, view
    view.render
  end
  
  def run
    while true
      print "\nNext file: "
      STDOUT.flush
      filename = STDIN.gets.chomp
      @model.update(filename)
      @view.render
    end
  end
end

m = WordFrequencyModel.new(ARGV.first)
v = WordFrequencyView.new(m)
c = WordFrequencyController.new(m, v)
c.run()
